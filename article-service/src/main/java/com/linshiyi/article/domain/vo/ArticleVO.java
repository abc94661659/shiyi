package com.linshiyi.article.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文章展示")
public class ArticleVO {

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
    @Schema(description = "文章内容")
    private String content;
}
