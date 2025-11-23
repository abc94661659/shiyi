package com.linshiyi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] SWAGGER_UI_RESOURCES = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**", // OpenAPI 规范 JSON/YAML 文件的路径
            "/webjars/swagger-ui/**" // Swagger UI 自身的静态资源
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认的表单登录（避免未认证时重定向到登录页）
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用HTTP基本认证
                .httpBasic(AbstractHttpConfigurer::disable)

                // 允许所有请求访问
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**", "/article/**", "/interaction/**", "/druid/**").permitAll()
                        .requestMatchers(SWAGGER_UI_RESOURCES).permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
