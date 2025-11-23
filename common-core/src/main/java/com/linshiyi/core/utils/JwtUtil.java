package com.linshiyi.core.utils;

import com.linshiyi.core.entity.dto.JwtPayloadDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {

    // 签名算法
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 生成JWT令牌
     *
     * @param payload 载荷信息
     * @param secret  密钥（从配置中获取）
     * @return token字符串
     */
    public static String generateToken(JwtPayloadDTO payload, String secret) {
        // 转换LocalDateTime为Date
        Instant issuedAtInstant = payload.getIssuedAt().atZone(ZoneId.systemDefault()).toInstant();
        Instant expirationInstant = payload.getExpiration().atZone(ZoneId.systemDefault()).toInstant();

        // 生成签名密钥
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // 构建token
        return Jwts.builder()
                .claim("userId", payload.getUserId()) // 用户ID
                .claim("userName", payload.getUserName())  // 自定义载荷：用户名
                .claim("status", payload.getStatus())
                .setIssuedAt(Date.from(issuedAtInstant))   // 签发时间
                .setExpiration(Date.from(expirationInstant)) // 过期时间
                .signWith(key, SIGNATURE_ALGORITHM)        // 签名
                .compact();
    }

    /**
     * 解析JWT令牌，获取载荷信息
     *
     * @param token  令牌
     * @param secret 密钥
     * @return 载荷DTO
     * @throws JwtException 解析失败（过期、签名错误等）
     */
    public static JwtPayloadDTO parseToken(String token, String secret) throws JwtException {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 转换Date为LocalDateTime
        LocalDateTime issuedAt = LocalDateTime.ofInstant(
                claims.getIssuedAt().toInstant(), ZoneId.systemDefault()
        );
        LocalDateTime expiration = LocalDateTime.ofInstant(
                claims.getExpiration().toInstant(), ZoneId.systemDefault()
        );

        return
                new JwtPayloadDTO(
                        claims.get("userId", String.class),
                        claims.get("userName", String.class),
                        claims.get("status", String.class),
                        issuedAt,
                        expiration
                );
    }

    /**
     * 验证token是否有效（未过期、签名正确）
     *
     * @param token  令牌
     * @param secret 密钥
     * @return 有效返回true，否则false（抛异常时捕获返回false）
     */
    public static boolean validateToken(String token, String secret) {
        try {
            parseToken(token, secret);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}