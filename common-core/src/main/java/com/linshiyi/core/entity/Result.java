package com.linshiyi.core.entity;


import com.linshiyi.core.enums.StatusCodeEnum;
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
     *
     * @param code 状态码 / Status code
     * @param msg  提示信息 / Prompt message
     * @param data 数据 / Data
     */
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应，只返回数据
     * 适用于查询操作
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    /**
     * 成功响应，只返回消息
     * 适用于创建、更新等操作
     */
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        result.setMsg(msg);
        // data字段默认为null，不需要显式设置
        return result;
    }

    /**
     * 成功响应，返回消息和数据
     * 适用于需要同时返回消息和数据的场景
     */
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(StatusCodeEnum.SUCCESS.getCode());
        if (msg != null) {
            result.setMsg(msg);
        } else {
            result.setMsg("success");
        }
        result.setData(data);
        return result;
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
