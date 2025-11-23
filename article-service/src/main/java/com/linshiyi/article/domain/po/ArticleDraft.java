package com.linshiyi.article.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ArticleDraft {
    private Long articleId;
    private String content;
    private LocalDateTime updateTime;
}
