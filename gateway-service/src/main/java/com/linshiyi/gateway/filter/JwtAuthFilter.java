package com.linshiyi.gateway.filter;

import com.linshiyi.core.entity.dto.JwtPayloadDTO;
import com.linshiyi.core.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 不需要验证的路径
    private static final String[] WHITE_LIST = {
            "/user/login",
            "/user/register",
            "/doc.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/article/getArticle/**",
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
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        token = token.substring(7); // 去掉"Bearer "前缀

        // 验证token
        try {
            JwtPayloadDTO payload = JwtUtil.parseToken(token, jwtSecret);
            // 验证通过，可将用户信息存入请求头，供下游服务使用
            exchange.getRequest().mutate()
                    .header("X-User-Id", payload.getUserId().toString())
                    .header("X-User-Name", payload.getUserName())
                    .header("X-User-Role", payload.getRole())
                    .build();
            return chain.filter(exchange);
        } catch (Exception e) {
            // 验证失败（过期、签名错误等）
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100; // 执行顺序：数字越小越先执行
    }
}