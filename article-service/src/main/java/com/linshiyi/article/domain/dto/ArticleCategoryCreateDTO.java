package com.linshiyi.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "文章分类创建参数")
public class ArticleCategoryCreateDTO {
    @Schema(description = "名称")
    @NotNull(message = "名称不能为空")
    private String name;
    @Schema(description = "父级ID")
    private Long parentId;
    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}
