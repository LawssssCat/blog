package org.example.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * 客户端工具类
 */
public class ServletUtils {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param content 待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String content) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
