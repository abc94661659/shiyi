package com.linshiyi.common.enums;

import lombok.Getter;

/**
 * 状态码枚举类：统一管理系统状态码
 * 状态码规则：
 * - 200xxx：成功相关
 * - 400xxx：客户端错误（参数错误、权限不足等）
 * - 500xxx：服务器错误（系统异常、数据库错误等）
 * - 600xxx：业务自定义错误（如资源不存在、操作失败等）
 */
@Getter
public enum StatusCodeEnum {

    // 通用成功
    SUCCESS(200, "操作成功"),

    // 客户端错误
    PARAM_ERROR(400, "参数校验失败"),
    AUTH_ERROR(401, "权限不足"),

    // 服务器错误
    SYSTEM_ERROR(500, "系统繁忙，请稍后再试"),

    // 业务自定义错误
    NOT_FOUND(404, "资源不存在"),
    BUSINESS_ERROR(600, "业务操作失败");


    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态描述
     */
    private final String msg;

    StatusCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}