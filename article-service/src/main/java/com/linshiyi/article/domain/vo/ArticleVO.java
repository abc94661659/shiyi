package com.linshiyi.article.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "文章展示")
@EqualsAndHashCode(callSuper = false)
public class ArticleVO extends ArticleListVO{

    @Schema(description = "文章内容")
    private String content;
}
