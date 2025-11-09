package com.linshiyi.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtPayloadDTO {

    private Long userId;

    private String userName;
    private String role;
    // token签发时间
    private LocalDateTime issuedAt;
    // token过期时间
    private LocalDateTime expiration;
}