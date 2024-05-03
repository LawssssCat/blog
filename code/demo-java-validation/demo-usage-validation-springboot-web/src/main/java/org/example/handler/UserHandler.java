package org.example.handler;

import org.example.entity.R;
import org.example.entity.UserParam;
import org.example.entity.UserVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 整理 {@link Valid} 和 {@link Validated} 的用法
 */
@RestController
@RequestMapping("/user")
@Validated // 💡在类上注解，开启方法参数校验（即：校验伴随校验注解的参数）
public class UserHandler {
    /**
     * 使用 {@link Valid} 触发校验，将全部校验失败结果存入 {@link BindingResult}
     */
    @GetMapping("/getUser")
    public R getUser(@RequestBody @Valid UserParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidatedErrors(bindingResult);
        }
        UserVo userVo = UserVo.builder()
                .id(param.getId())
                .username(param.getUsername())
                .build();
        return R.SUCCESS(userVo);
    }

    /**
     * {@link Validated} 优点一：在方法参数中使用，可以指定校验 “分组” 功能
     */
    @PostMapping("/insertUser")
    public R insertUser(@RequestBody @Validated(value = {UserParam.Insert.class, Default.class}) UserParam param,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidatedErrors(bindingResult);
        }
        return R.SUCCESS("ok");
    }

    /**
     * {@link Validated} 优点二：在直接对方法参数开启校验功能
     * （对校验失败的抛出 {@link javax.validation.ConstraintViolationException} 异常；
     * 对参数转换错误的抛出 {@link org.springframework.web.method.annotation.MethodArgumentTypeMismatchException} 异常;
     * 参数缺失异常 {@link org.springframework.web.bind.MissingServletRequestParameterException}）
     */
    @GetMapping("/reflect")
    public R reflect(@RequestParam("id") @NotNull @Positive Integer num) {
        return R.SUCCESS(num);
    }

    private static R handleValidatedErrors(BindingResult bindingResult) {
        List<Map<String, Object>> errors = bindingResult.getAllErrors()
                .stream().map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ObjectName", e.getObjectName());
                    map.put("DefaultMessage", e.getDefaultMessage());
                    // map.put("Arguments", e.getArguments()); // 全部入参
                    if (e instanceof FieldError) {
                        map.put("Field", ((FieldError) e).getField());
                        map.put("RejectedValue", ((FieldError) e).getRejectedValue());
                    }
                    return map;
                }).collect(Collectors.toList());
        return R.ERROR(errors);
    }
}
