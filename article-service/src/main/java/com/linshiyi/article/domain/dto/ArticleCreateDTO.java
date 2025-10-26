package com.linshiyi.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建文章参数")
public class ArticleCreateDTO {
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;
    @Schema(description = "摘要")
    private String summary;
    @Schema(description = "作者ID")
    @NotNull(message = "作者ID不能为空")
    private Long authorId;
    @Schema(description = "分类ID")
    @NotNull(message = "分类ID不能为空")
    private Integer categoryId;
    @Schema(description = "封面图片")
    private String coverImage;
    @Schema(description = "文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;
}
