package com.linshiyi.user.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户角色")
public class UserRole {
    @Schema(description = "用户角色id")
    private Long id;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "角色id")
    private Long roleId;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
