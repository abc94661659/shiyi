package com.linshiyi.interaction.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDTO {

    @Schema(description = "实体类型")
    @NotBlank(message = "实体类型不能为空")
    private String entityType;

    @Schema(description = "实体id")
    @NotNull(message = "实体id不能为空")
    private Long entityId;
}
