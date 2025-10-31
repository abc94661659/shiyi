package com.linshiyi.interaction.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建评论参数")
public class CommentCreateDTO {
    @Schema(description = "实体类型")
    @NotBlank(message = "实体类型不能为空")
    private String entityType;

    @Schema(description = "实体id")
    @NotNull(message = "实体id不能为空")
    private Long entityId;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "父级评论id")
    private Long parentId;
}
