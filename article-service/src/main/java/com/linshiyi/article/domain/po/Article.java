package com.linshiyi.article.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {

    private Long id;
    private String title;
    private String summary;
    private Long authorId;
    private Integer categoryId;
    private String coverImage;
    private Integer viewCount;
    private Integer commentCount;
    private Integer status;
    /**
     * 0-正常 1-删除
     */
    private Integer isDeleted;
    /**
     * 0-普通 1-置顶
     */
    private Integer isTop;
    /**
     * 审核人Id
     */
    private Long auditId;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
