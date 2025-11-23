package com.linshiyi.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtPayloadDTO {

    private String userId;

    private String userName;
    private String status;
    // token签发时间
    private LocalDateTime issuedAt;
    // token过期时间
    private LocalDateTime expiration;
}