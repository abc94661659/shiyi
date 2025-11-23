package com.linshiyi.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class UserContext {

    public static final String HEADER_USER_ID = "X-User-Id";

    public static String getCurrentUserIdStr() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("无法获取当前请求上下文，请确保在 Web 请求线程中调用");
        }
        HttpServletRequest request = attrs.getRequest();
        return request.getHeader(HEADER_USER_ID);
    }
    
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
            // 可选：记录 warn 日志，例如：
            log.warn("Invalid X-User-Id format: {}", userIdStr);
            return null;
        }
    }
}