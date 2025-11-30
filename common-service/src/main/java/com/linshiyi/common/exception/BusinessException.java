package com.linshiyi.common.exception;


import com.linshiyi.core.enums.StatusCodeEnum;
import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private final Integer code;
    /**
     * http 状态码
     */
    private final Integer httpStatus;


    public BusinessException(StatusCodeEnum status, String message) {
        super(message);
        this.code = status.getCode();
        this.httpStatus = status.getHttpStatus();
    }

    /**
     * 默认错误码为 600
     */
    public BusinessException(String message) {
        this(StatusCodeEnum.BUSINESS_ERROR, message);
    }
}
