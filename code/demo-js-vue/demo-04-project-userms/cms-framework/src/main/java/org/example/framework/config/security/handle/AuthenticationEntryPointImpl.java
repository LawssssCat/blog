package org.example.framework.config.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.common.constant.HttpStatus;
import org.example.common.core.domain.AjaxResult;
import org.example.common.utils.MessageUtils;
import org.example.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

/**
 * 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = MessageUtils.message("user.access.not", request.getRequestURI());
        String json = JSON.toJSONString(AjaxResult.error(code, msg));
        ServletUtils.renderString(response, json);
    }
}
