package org.example.common.utils.web;

import javax.servlet.http.HttpServletRequest;

import org.example.common.utils.StringUtils;

import sun.net.util.IPAddressUtil;

/**
 * 获取IP方法
 * <p>
 * 注意：仅支持IPv4
 */
public class IpUtils {
    /**
     * 未知地址
     */
    public static final String UNKNOWN = "XX XX";

    /**
     * 获取客户端IP
     *
     * @return IP地址
     */
    public static String getClientIpAddr() {
        return getIpAddr(ServletUtils.getRequest());
    }

    private static String getIpAddr(HttpServletRequest request) {

        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(subIp)) {
                    continue;
                }
                ip = subIp;
                break;
            }
        }
        return StringUtils.substring(ip, 0, 255);
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param ip IP地址
     * @return 结果
     */
    public static boolean isInternalIp(String ip) {
        if ("127.0.0.1".equals(ip)) {
            return true;
        }
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        return isInternalIp(addr);
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param addr byte地址
     * @return 结果
     */
    private static boolean isInternalIp(byte[] addr) {
        if (StringUtils.isNull(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte)0xAC;
        final byte SECTION_3 = (byte)0x10;
        final byte SECTION_4 = (byte)0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte)0xC0;
        final byte SECTION_6 = (byte)0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    public static String getRealAddressByIP(String ip) {
        if (isInternalIp(ip)) {
            return "内网IP";
        }
        return UNKNOWN; // TODO 请求 http://whois.pconline.com.cn/ipJson.jsp
    }
}
