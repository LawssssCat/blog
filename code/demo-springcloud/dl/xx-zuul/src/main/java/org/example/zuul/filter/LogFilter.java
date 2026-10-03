package org.example.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class LogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 多种路由类型，和 iptable 类似
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 顺序小的先执行
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String serverName = request.getServerName();
        log.info("<======= {} {} from {}:{}",
                serverName, request.getRequestURI(),
                request.getRemoteAddr(), request.getRemotePort());
        return null;
    }
}
