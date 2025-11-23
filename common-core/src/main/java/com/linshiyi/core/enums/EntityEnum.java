package com.linshiyi.core.enums;

import lombok.Getter;

@Getter
public enum EntityEnum {
    ARTICLE("ARTICLE"),
    COMMENT("COMMENT"),
    USER("USER");

    private final String value;

    EntityEnum(String value) {
        this.value = value;
    }

    public static EntityEnum fromValue(String value) {
        for (EntityEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的实体类型: " + value);
    }
}
