package com.linshiyi.core.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {

    private Long id;

    private Long userId;

    private String userName;
    // 操作类型
    private String operationType;
    // 操作描述
    private String operationDesc;
    // 资源类型
    private String resourceType;
    // 资源ID
    private Long resourceId;
    // 请求方法
    private String requestMethod;
    // 请求URI
    private String requestUri;
    // IP地址
    private String ipAddress;
    // User-Agent
    private String userAgent;
    // 操作结果
    private String resultStatus;
    // 错误信息
    private String errorMessage;

    private LocalDateTime createTime;
}
