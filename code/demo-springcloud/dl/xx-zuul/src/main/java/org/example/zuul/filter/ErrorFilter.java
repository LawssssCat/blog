package org.example.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.model.RestResult;
import org.example.commons.util.JsonUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class ErrorFilter extends SendErrorFilter {
    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        ZuulException throwable = (ZuulException) currentContext.getThrowable();
        log.error("接口异常拦截：{}", throwable.getMessage());
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        int code = throwable.nStatusCode;
        String msg = throwable.getMessage();
        String responseBody;
        try {
            responseBody = JsonUtils.getObjectMapper().writeValueAsString(new RestResult(code, msg, null));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
