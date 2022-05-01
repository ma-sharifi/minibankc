package com.example.minibankc.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
public class AppUtil {

    public static String getRequestClientIpComplete(HttpServletRequest req) {
        String clientIp = req.getHeader("X-Real-IP");
        if (clientIp == null || "".equals(clientIp)) { // extract from forward ips
            String ipForwarded = req.getHeader("X-FORWARDED-FOR");
            String[] ips = ipForwarded == null ? null : ipForwarded.split(",");
            clientIp = (ips == null || ips.length == 0) ? null : ips[0];
            // extract from remote addr
            clientIp = (clientIp == null || clientIp.isEmpty()) ? req.getRemoteAddr() : clientIp;
        }
        if("0:0:0:0:0:0:0:1".equals(clientIp)) clientIp="127.0.0.1";
        return clientIp;
    }
}
