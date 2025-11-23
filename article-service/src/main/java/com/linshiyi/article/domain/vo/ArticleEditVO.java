package com.linshiyi.article.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文章编辑视图")
public class ArticleEditVO extends ArticleListVO {
    @Schema(description = "当前可编辑的内容（优先草稿）")
    private String content;

    @Schema(description = "是否为草稿状态")
    private Boolean isDraft;

    @Schema(description = "是否为审核状态")
    private Integer status;
}