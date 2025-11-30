package com.linshiyi.common.exception;


import com.linshiyi.core.entity.Result;
import com.linshiyi.core.enums.StatusCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e, HttpServletResponse response) {
        log.error("业务异常：{}", e.getMessage());
        response.setStatus(e.getHttpStatus());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理DTO参数校验异常（如@NotBlank、@NotNull校验失败）
     * 对应Controller中@Valid注解触发的校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        // 获取校验失败的第一个错误信息
        String errorMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("参数校验异常：{}", errorMsg);
        response.setStatus(StatusCodeEnum.PARAM_ERROR.getHttpStatus());
        return Result.fail(StatusCodeEnum.PARAM_ERROR, errorMsg);
    }

    /**
     * 处理其他未捕获的异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, HttpServletResponse response) {
        log.error("系统异常：", e);
        response.setStatus(StatusCodeEnum.SYSTEM_ERROR.getHttpStatus());
        return Result.fail(StatusCodeEnum.SYSTEM_ERROR, "系统繁忙，请稍后再试");
    }
}
