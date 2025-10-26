package com.linshiyi.comment.domain.dto;

import com.linshiyi.common.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "评论分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class CommentQueryDTO extends QueryDTO {
    @Schema(description = "文章id")
    @NotNull(message = "文章id不能为空")
    private Long articleId;
}
