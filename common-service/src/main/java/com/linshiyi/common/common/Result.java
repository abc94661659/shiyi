package com.linshiyi.common.common;

import com.linshiyi.common.enums.StatusCodeEnum;
import lombok.Data;

@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    /**
     * 默认构造函数
     * Default constructor
     */
    private Result() {
    }

    /**
     * 带参数的构造函数
     * Constructor with parameters
     * @param code 状态码 / Status code
     * @param msg 提示信息 / Prompt message
     * @param data 数据 / Data
     */
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setMsg(StatusCodeEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }
    public static <T> Result<T> fail(StatusCodeEnum status) {
        Result<T> result = new Result<>();
        result.setCode(status.getCode());
        result.setMsg(status.getMsg());
        result.setData(null);
        return result;
    }
    public static <T> Result<T> fail(StatusCodeEnum status, String customMsg) {
        Result<T> result = new Result<>();
        result.setCode(status.getCode());
        result.setMsg(customMsg);
        result.setData(null);
        return result;
    }
    public static <T> Result<T> fail(String msg) {
        return fail(StatusCodeEnum.SYSTEM_ERROR, msg);  // 默认500
    }
    public static <T> Result<T> fail(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
