package com.linshiyi.interaction.domain.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class CommentPageVO  {
    /**
     * 分页信息（基于父评论）
     */
    private PageInfo<CommentVO> pageInfo;

    /**
     * 父评论总数
     */
    private long totalParentComments;


}
