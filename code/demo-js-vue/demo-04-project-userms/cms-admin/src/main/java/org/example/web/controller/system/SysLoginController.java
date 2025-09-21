package org.example.web.controller.system;

import javax.annotation.Resource;

import org.example.common.constant.Constants;
import org.example.common.core.domain.AjaxResult;
import org.example.common.core.domain.model.LoginBody;
import org.example.framework.web.service.SysLoginService;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        // TODO
        // LoginUser loginUser = SecurityUtils.getLoginUser();
        // SysUser user = loginUser.getUser();
        // // 角色集合
        // Set<String> roles = permissionService.getRolePermission(user);
        // // 权限集合
        // Set<String> permissions = permissionService.getMenuPermission(user);
        // if (!loginUser.getPermissions().equals(permissions))
        // {
        // loginUser.setPermissions(permissions);
        // tokenService.refreshToken(loginUser);
        // }
        // AjaxResult ajax = AjaxResult.success();
        // ajax.put("user", user);
        // ajax.put("roles", roles);
        // ajax.put("permissions", permissions);
        // return ajax;
        return AjaxResult.error("维护中");
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        // TODO
        // Long userId = SecurityUtils.getUserId();
        // List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        // return AjaxResult.success(menuService.buildMenus(menus));
        return AjaxResult.error("维护中");
    }
}
