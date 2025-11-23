package com.linshiyi.interaction.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLike {
    private Long userId;
    private String entityType;
    private Long entityId;
    private LocalDateTime createTime;
}
