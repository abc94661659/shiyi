package com.linshiyi.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        // 1. 配置跨域参数
        CorsConfiguration config = new CorsConfiguration();
        // 允许的来源（根据实际前端地址调整，*表示允许所有）
        config.addAllowedOriginPattern("http://localhost:5173");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的请求方法（GET/POST等）
        config.addAllowedMethod("*");
        // 允许携带Cookie
        config.setAllowCredentials(true);
        // 预检请求的缓存时间（秒），减少重复预检
        config.setMaxAge(3600L);

        // 2. 配置路径匹配规则（对所有路径生效）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        // 3. 创建并返回过滤器
        return new CorsWebFilter(source);
    }
}