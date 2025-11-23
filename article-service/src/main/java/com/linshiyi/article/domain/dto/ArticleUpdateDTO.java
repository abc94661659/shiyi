package com.linshiyi.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新文章参数")
public class ArticleUpdateDTO {
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    @Min(value = 1, message = "标题长度不能小于1")
    @Max(value = 100, message = "标题长度不能大于100")
    private String title;
    @Schema(description = "摘要")
    @Min(value = 1, message = "摘要长度不能小于1")
    @Max(value = 500, message = "摘要长度不能大于100")
    private String summary;
    @Schema(description = "分类ID")
    @NotNull(message = "分类ID不能为空")
    private Integer categoryId;
    @Schema(description = "封面图片")
    private String coverImage;
    @Schema(description = "文章内容")
    private String content;
}
