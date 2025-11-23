package com.linshiyi.core.entity.vo;

import lombok.Data;


@Data
public class UserBriefVO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;
}