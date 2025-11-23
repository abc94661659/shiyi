package com.linshiyi.interaction.domain.dto;

import com.linshiyi.core.enums.EntityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLikeDTO {

    @Schema(description = "实体类型", allowableValues = {"ARTICLE", "COMMENT", "USER"})
    @NotNull(message = "实体类型不能为空")
    private EntityEnum entityType;

    @Schema(description = "实体id")
    @NotNull(message = "实体id不能为空")
    @Min(value = 1, message = "实体id必须大于0")
    private Long entityId;
}
