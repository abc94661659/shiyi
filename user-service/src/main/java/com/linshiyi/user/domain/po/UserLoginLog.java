package com.linshiyi.user.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户登录日志")
public class UserLoginLog {
    @Schema(description = "用户登录日志id")
    private Long id;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "登录ip")
    private String loginIp;
    @Schema(description = "登录位置")
    private String loginLocation;
    @Schema(description = "登录设备")
    private String loginDevice;
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;
    @Schema(description = "登录状态")
    private Integer status;
}
