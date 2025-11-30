package com.linshiyi.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class UserContext {

    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USER_NAME = "X-User-Name";

    public static Long getCurrentUserId() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            // 非 Web 请求线程（如定时任务、MQ 消费者）
            return null;
        }

        HttpServletRequest request = attrs.getRequest();
        String userIdStr = request.getHeader(HEADER_USER_ID);
        if (userIdStr == null || userIdStr.isEmpty()) {
            // 未登录或网关未注入
            return null;
        }

        try {
            return Long.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            log.warn("X-User-Id 格式无效: {}", userIdStr);
            return null;
        }
    }

    public static String getCurrentUsername() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null; // 非 Web 环境
        }
        String username = attrs.getRequest().getHeader(HEADER_USER_NAME);
        if (username == null || username.isBlank()) {
            return null;
        }
        return username.trim();
    }
}