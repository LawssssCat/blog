package org.example.framework.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.common.core.domain.AjaxResult;
import org.example.common.utils.MessageUtils;
import org.example.common.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

/**
 * 自定义退出处理类 返回成功
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        // TODO 清理toekn信息

        // response
        String msg = MessageUtils.message("user.logout.success");
        String json = JSON.toJSONString(AjaxResult.success(msg));
        ServletUtils.renderString(response, json);
    }
}
