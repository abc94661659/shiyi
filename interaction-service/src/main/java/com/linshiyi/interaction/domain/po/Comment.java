package com.linshiyi.interaction.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private String entityType;
    private Long entityId;
    private Long userId;
    private String content;
    private Long parentId;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
