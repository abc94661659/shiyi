package com.linshiyi.article.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleCategory {
    private Long id;
    private String name;
    private Long parentId;
    private Integer level;
    private Integer sort;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
