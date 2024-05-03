package org.example.handler;

import org.example.entity.R;
import org.example.entity.TaskParam;
import org.example.entity.TaskVo;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 整理校验异常
 */
@RestController
@RequestMapping("/task")
@Validated
public class TaskHandler {
    /**
     * 对于 {@link RequestParam}/{@link PathVariable} 修饰的 URL 参数：
     * 在类上用 {@link Validated} 修饰且参数用 {@link javax.validation.Constraint} 修饰时，
     * 校验失败将抛出 {@link ConstraintViolationException} 异常
     */
    @GetMapping("/param")
    public R param(@RequestParam("num") @Positive @NotNull Integer num) {
        return R.SUCCESS(num);
    }
    @GetMapping("/param/{num}")
    public R paramPath(@PathVariable("num") @Positive @NotNull Integer num) {
        return R.SUCCESS(num);
    }

    /**
     * 对于默认的表单参数：
     * 参数用 {@link Valid}/{@link Validated} 修饰时、
     * 校验失败将抛出 {@link BindException} 异常
     */
    @GetMapping("/formValid")
    public R formValid(@Valid TaskParam param) {
        TaskVo taskVo = TaskVo.builder()
                .id(param.getId())
                .build();
        return R.SUCCESS(taskVo);
    }
    @GetMapping("/formValidated")
    public R formValidated(@Validated TaskParam param) {
        TaskVo taskVo = TaskVo.builder()
                .id(param.getId())
                .build();
        return R.SUCCESS(taskVo);
    }

    /**
     * 对于 {@link RequestBody} 修饰的 json 参数：
     * 参数用 {@link Valid}/{@link Validated} 修饰时、
     * 校验失败将抛出 {@link MethodArgumentNotValidException} 异常
     */
    @GetMapping("/jsonValid")
    public R jsonValid(@RequestBody @Valid TaskParam param) {
        TaskVo taskVo = TaskVo.builder()
                .id(param.getId())
                .build();
        return R.SUCCESS(taskVo);
    }
    @GetMapping("/jsonValidated")
    public R jsonValidated(@RequestBody @Validated TaskParam param) {
        TaskVo taskVo = TaskVo.builder()
                .id(param.getId())
                .build();
        return R.SUCCESS(taskVo);
    }

    /**
     * 通过 {@link ExceptionHandler} 拦截当前类的 {@link ConstraintViolationException} 异常
     * （如果考虑拦截全部 handler 异常，可以将 {@link ExceptionHandler} 定义在 {@link ControllerAdvice} 修饰的类中）
     */
    @ExceptionHandler({ConstraintViolationException.class}) // url 参数提交
    public R handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<Map<String, Object>> collect = constraintViolations.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("PropertyPath", e.getPropertyPath().toString());
            map.put("InvalidValue", e.getInvalidValue());
            map.put("Message", e.getMessage());
            return map;
        }).collect(Collectors.toList());
        return R.ERROR(collect);
    }
    @ExceptionHandler({BindException.class}) // 表单提交
    public R handleBindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return getR(bindingResult);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class}) // json 提交
    public R handleBindException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return getR(bindingResult);
    }

    private static R getR(BindingResult bindingResult) {
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
