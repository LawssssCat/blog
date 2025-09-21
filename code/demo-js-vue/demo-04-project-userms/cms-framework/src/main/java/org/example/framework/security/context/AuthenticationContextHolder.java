package org.example.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息
 */
public class AuthenticationContextHolder {
    /**
     * 全应用唯一标识
     */
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    /**
     * @return 当前线程的认证上下文
     */
    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
