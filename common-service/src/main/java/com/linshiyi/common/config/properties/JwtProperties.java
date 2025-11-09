package com.linshiyi.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt") // 配置前缀
public class JwtProperties {
    // 签名密钥（必须足够复杂，建议至少32位）
    private String secret;
    // 过期时间（单位：分钟）
    private Integer expireMinutes = 30;
}