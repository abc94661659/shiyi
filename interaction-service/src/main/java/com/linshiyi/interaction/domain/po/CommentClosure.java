package com.linshiyi.interaction.domain.po;

import lombok.Data;

@Data
public class CommentClosure {
    /**
     * 祖先id
     */
    private Long ancestorId;
    /**
     * 后代id
     */
    private Long descendantId;
    /**
     * 层级
     */
    private Integer depth;
}
