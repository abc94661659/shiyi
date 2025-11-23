package com.linshiyi.core.enums;

import lombok.Getter;

/**
 * 通用状态枚举（适用于启用/禁用/删除场景）
 */
@Getter
public enum StatusEnum {
    // 正常状态（未禁用、未删除）
    NORMAL(0, "NORMAL"),
    DELETED(1, "DELETED"),
    DISABLED(2, "DISABLED");
    // 数据库存储的状态值
    private final Integer code;
    // 状态描述
    private final String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static String getByCode(Integer code) {
        for (StatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status.desc;
            }
        }
        // 默认正常状态
        return StatusEnum.NORMAL.desc;
    }
}