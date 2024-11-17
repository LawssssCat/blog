package org.example.web.system;

import javax.annotation.Resource;

import org.example.common.constant.Constants;
import org.example.common.core.domain.AjaxResult;
import org.example.common.core.domain.model.LoginBody;
import org.example.framework.web.service.SysLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证
 */
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService sysLoginService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();

        // 生成令牌
        String token = sysLoginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);

        return ajax;
    }
}
