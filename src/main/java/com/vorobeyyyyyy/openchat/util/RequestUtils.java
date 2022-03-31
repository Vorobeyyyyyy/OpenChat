package com.vorobeyyyyyy.openchat.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RequestUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes)
                .orElseThrow(() -> new IllegalStateException("Called outside of request scope"))
                .getRequest();
    }

    public static String getIp() {
        return getRequest().getRemoteAddr();
    }

    public static String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }
}
