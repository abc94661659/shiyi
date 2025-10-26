package com.linshiyi.article.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleContent {
    private Long id;
    private Long articleId;
    private String content;
    /**
     * 文章内容版本号
     */
    private Integer contentVersion;
    private LocalDateTime createTime;
}
