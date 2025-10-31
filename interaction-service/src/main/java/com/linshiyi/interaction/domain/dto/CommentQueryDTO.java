package com.linshiyi.interaction.domain.dto;

import com.linshiyi.common.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "评论分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class CommentQueryDTO extends QueryDTO {

    @Schema(description = "实体类型")
    @NotBlank(message = "实体类型不能为空")
    private String entityType;

    @Schema(description = "实体id")
    @NotNull(message = "实体id不能为空")
    private Long entityId;
}
