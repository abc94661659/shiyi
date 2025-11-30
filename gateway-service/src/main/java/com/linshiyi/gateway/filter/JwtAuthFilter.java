package com.linshiyi.gateway.filter;

import com.linshiyi.core.entity.dto.JwtPayloadDTO;
import com.linshiyi.core.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 不需要验证的路径
    private static final String[] WHITE_LIST = {
            "/doc.html",
            "/user/login",
            "/user/register",
            "/article/*",
            "/interaction/comment/queryParentComment"
    };
    private static final List<PathPattern> whitePatterns = Arrays.stream(WHITE_LIST)
            .map(PathPatternParser.defaultInstance::parse)
            .toList();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        // 白名单路径直接放行
        boolean isWhiteListed = whitePatterns.stream()
                .anyMatch(pattern -> pattern.matches(PathContainer.parsePath(path)));

        if (isWhiteListed) {
            return chain.filter(exchange);
        }

        // 从请求头获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorized(exchange, "缺少有效Token");
        }
        token = token.substring(7);

        // 验证token
        try {
            JwtPayloadDTO payload = JwtUtil.parseToken(token, jwtSecret);
            String userId = payload.getUserId();
            // 验证通过，可将用户信息存入请求头，供下游服务使用
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Name", safeHeader(payload.getUserName()))
                    .header("X-User-Status", payload.getStatus() != null ? payload.getStatus() : "0")
                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        } catch (Exception e) {
            // 验证失败（过期、签名错误等）
            return unauthorized(exchange, "Token无效或已过期: " + e.getMessage());
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        String body = String.format("{\"code\":401,\"msg\":\"%s\",\"data\":null}",
                message.replace("\"", "\\\""));

        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer)).then(Mono.defer(response::setComplete));
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private String safeHeader(String value) {
        return value != null ? value : "";
    }
}