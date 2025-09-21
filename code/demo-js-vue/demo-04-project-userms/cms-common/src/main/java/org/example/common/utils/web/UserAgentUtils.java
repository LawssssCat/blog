package org.example.common.utils.web;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;

/**
 * 解析 UserAgent 的工具
 */
public class UserAgentUtils {
    private static final String HEADER = "User-Agent";

    private static final UserAgentParser PARSER;

    static {
        try {
            PARSER = new UserAgentService().loadParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserAgent() {
        return ServletUtils.getRequest().getHeader(HEADER);
    }

    private static Capabilities getCapabilities() {
        return PARSER.parse(getUserAgent());
    }

    public static String getBrowser() {
        return getCapabilities().getBrowser();
    }

    public static String getPlatform() {
        return getCapabilities().getPlatform();
    }
}
