package com.linshiyi.comment.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentLike {
    private Long id;
    private Long commentId;
    private Long userId;
    private Integer status;
    private LocalDateTime createTime;
}
