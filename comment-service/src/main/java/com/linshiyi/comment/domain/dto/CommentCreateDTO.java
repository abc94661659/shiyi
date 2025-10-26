package com.linshiyi.comment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建评论参数")
public class CommentCreateDTO {

    @Schema(description = "文章id")
    @NotNull(message = "文章id不能为空")
    private Long articleId;

    @Schema(description = "父级评论id")
    private Long parentId;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

}
