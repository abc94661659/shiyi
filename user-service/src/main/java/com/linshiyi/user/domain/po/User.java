package com.linshiyi.user.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private String role;

    private Integer status;

    private LocalDateTime LastLoginTime;

    private String LastLoginIp;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
