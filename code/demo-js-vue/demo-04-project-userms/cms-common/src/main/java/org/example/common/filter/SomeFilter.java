package org.example.common.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试
 *
 * @see <a href="https://blog.csdn.net/qq_45515182/article/details/127217852">FilterRegistrationBean用法</a>
 * @see <a href="https://blog.csdn.net/qq_35207086/article/details/120091221">FilterRegistrationBean用法</a>
 */
@Slf4j
public class SomeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.debug("filter: {}", str(request));
        chain.doFilter(request, response);
    }

    private String str(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest)request).getMethod()
                + " " + ((HttpServletRequest)request).getRequestURI();
        } else {
            return request.toString();
        }
    }

    @Override
    public void destroy() {}
}
