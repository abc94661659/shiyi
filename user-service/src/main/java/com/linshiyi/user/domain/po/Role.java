package com.linshiyi.user.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色")
public class Role {
    @Schema(description = "角色id")
    private Long id;
    @Schema(description = "角色名")
    private String roleName;
    @Schema(description = "角色权限")
    private String permissions;
    @Schema(description = "角色描述")
    private String description;
    @Schema(description = "状态")
    private Integer status;
}
