package com.linshiyi.interaction.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {
    private Long id;
    private Long userId;
    private String entityType;
    private Long entityId;
    private Integer isDeleted;
    private LocalDateTime createTime;
}
