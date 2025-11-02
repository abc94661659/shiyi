package com.linshiyi.article.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "文章列表展示")
public class ArticleListVO  {
    @Schema(description = "文章ID")
    private Long id;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "摘要")
    private String summary;
    @Schema(description = "作者ID")
    private Long authorId;
    @Schema(description = "作者名称")
    private String authorName;
    @Schema(description = "分类ID")
    private Integer categoryId;
    @Schema(description = "分类名称")
    private String categoryName;
    @Schema(description = "封面图片")
    private String coverImage;
}
