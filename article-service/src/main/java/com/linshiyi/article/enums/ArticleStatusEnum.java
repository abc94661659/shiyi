package com.linshiyi.article.enums;

import lombok.Getter;

/**
 * 文章状态枚举
 */
@Getter
public enum ArticleStatusEnum {
    // 未发布，仅保存
    DRAFT(0, "草稿"),
    // 正常发布状态
    PUBLISHED(1, "已发布"),
    // 待审核（若有审核流程）
    REVIEWING(2, "审核中"),
    // 审核未通过
    REJECTED(3, "已驳回"),
    // 已发布后被下架
    OFFLINE(4, "已下线");

    private final Integer code;
    private final String desc;

    ArticleStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 新增根据code获取枚举的方法（便于数据库查询后转换）
    public static ArticleStatusEnum getByCode(Integer code) {
        for (ArticleStatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}