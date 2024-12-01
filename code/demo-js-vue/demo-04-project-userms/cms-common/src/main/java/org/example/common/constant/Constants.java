package org.example.common.constant;

import java.util.Locale;

/**
 * 通用常量信息
 */
public interface Constants {
    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * 系统语言
     */
    Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    /**
     * 令牌
     */
    String TOKEN = "token";

    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    String LOGIN_USER_KEY = "login_user_key";
}
