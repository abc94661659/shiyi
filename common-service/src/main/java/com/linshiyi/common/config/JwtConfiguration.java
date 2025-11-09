package com.linshiyi.common.config;

import com.linshiyi.common.config.properties.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(JwtProperties.class)
public class JwtConfiguration {

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }
}