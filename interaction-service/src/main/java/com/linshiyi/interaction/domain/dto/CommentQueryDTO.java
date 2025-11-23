package com.linshiyi.interaction.domain.dto;


import com.linshiyi.core.entity.dto.QueryDTO;
import com.linshiyi.core.enums.EntityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "评论分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class CommentQueryDTO extends QueryDTO {

    @Schema(description = "实体类型", allowableValues = {"ARTICLE", "COMMENT"})
    @NotNull(message = "实体类型不能为空")
    private EntityEnum entityType;

    @Schema(description = "实体id")
    @NotNull(message = "实体id不能为空")
    private Long entityId;
}
