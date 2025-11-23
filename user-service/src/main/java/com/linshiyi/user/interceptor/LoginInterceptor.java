package com.linshiyi.user.interceptor;

import com.linshiyi.core.annotation.RequireLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 非 Controller 方法直接放行（比如静态资源）
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // 检查方法是否有 @RequireLogin 注解
        if (handlerMethod.getMethodAnnotation(RequireLogin.class) != null) {
            String userId = request.getHeader("X-User-Id");
            if (userId == null || userId.isEmpty()) {
                // 返回 JSON 格式的 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.write("{\"code\":401,\"msg\":\"请先登录\"}");
                }
                return false;
            }
        }
        return true;
    }
}