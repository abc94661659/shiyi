package com.linshiyi.interaction.service;

import com.github.pagehelper.PageInfo;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.vo.CommentVO;

public interface CommentService {

    void createComment(CommentCreateDTO commentCreateDTO);

    /**
     * 查询父级评论
     *
     * @param commentQueryDTO 查询参数
     * @return 父级评论
     */
    PageInfo<CommentVO> queryParentComment(CommentQueryDTO commentQueryDTO);
}
