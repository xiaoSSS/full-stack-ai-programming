package com.xiaoss.starter.web.context;

import jakarta.servlet.http.HttpServletRequest;

public final class IpUtil {

    private static final String[] IP_HEADER_CANDIDATES = new String[]{
        "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_CLIENT_IP"
    };

    private IpUtil() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (isValidIp(ip)) {
                return firstIp(ip);
            }
        }
        return request.getRemoteAddr();
    }

    private static boolean isValidIp(String ip) {
        return ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip);
    }

    private static String firstIp(String ip) {
        int commaIndex = ip.indexOf(',');
        return commaIndex > 0 ? ip.substring(0, commaIndex).trim() : ip;
    }
}
