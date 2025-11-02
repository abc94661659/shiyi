package com.linshiyi.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "文章分类创建参数")
public class ArticleCategoryUpdateDTO {
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "父级ID")
    private Long parentId;
    @Schema(description = "排序")
    private Integer sort;
    @Schema(description = "是否删除")
    private Integer isDeleted;
}
