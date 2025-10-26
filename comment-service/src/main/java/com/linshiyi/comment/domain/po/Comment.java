package com.linshiyi.comment.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer status;
    /**
     * 点赞数
     */
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
