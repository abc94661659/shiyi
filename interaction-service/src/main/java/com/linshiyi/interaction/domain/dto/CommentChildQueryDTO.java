package com.linshiyi.interaction.domain.dto;

import com.linshiyi.common.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "子评论分页查询参数")
public class CommentChildQueryDTO extends CommentQueryDTO {
    @Schema(description = "父评论ID")
    @NotNull(message = "父评论ID不能为空")
    private Long parentId;
}