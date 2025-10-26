package com.linshiyi.common.config;


import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 全局时间格式化配置（JSON序列化时生效）
 */
@Configuration
public class DateTimeConfig {

    // 默认时间格式
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 配置LocalDateTime的JSON序列化格式
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 序列化LocalDateTime时使用指定格式
            builder.serializerByType(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_PATTERN)));
        };
    }
}