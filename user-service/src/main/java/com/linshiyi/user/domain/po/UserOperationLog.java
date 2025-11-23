package com.linshiyi.user.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOperationLog {
    @Schema(description = "用户操作日志ID")
    private Long id;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "用户名称")
    private String userName;
    @Schema(description = "操作类型")
    private String operationType;
    @Schema(description = "操作描述")
    private String operationDesc;
    @Schema(description = "资源类型")
    private String resourceType;
    @Schema(description = "资源ID")
    private Long resourceId;
    @Schema(description = "请求方式")
    private String requestMethod;
    @Schema(description = "请求路径")
    private String requestUri;
    @Schema(description = "IP地址")
    private String ipAddress;
    @Schema(description = "客户端标识")
    private String userAgent;
    @Schema(description = "操作结果")
    private String requestStatus;
    @Schema(description = "错误信息")
    private String errorMessage;
    @Schema(description = "操作事件")
    private LocalDateTime createTime;
}
