package com.linshiyi.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新文章参数")
public class ArticleUpdateDTO {
    @Schema(description = "文章ID")
    @NotNull(message = "文章ID不能为空")
    private Long id;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "摘要")
    private String summary;
    @Schema(description = "分类ID")
    private Integer categoryId;
    @Schema(description = "封面图片")
    private String coverImage;
    @Schema(description = "文章内容")
    private String content;
}
