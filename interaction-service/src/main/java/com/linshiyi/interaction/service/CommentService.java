package com.linshiyi.interaction.service;

import com.linshiyi.core.entity.PageResult;
import com.linshiyi.interaction.domain.dto.CommentChildQueryDTO;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.vo.CommentVO;

public interface CommentService {

    void createComment(CommentCreateDTO commentCreateDTO, Long userId);

    /**
     * 查询父级评论
     *
     * @param commentQueryDTO 查询参数
     * @return 父级评论
     */
    PageResult<CommentVO> queryParentComment(CommentQueryDTO commentQueryDTO, Long userId);

    /**
     * 查询子评论
     *
     * @param commentChildQueryDTO 查询参数
     * @return 子评论
     */
    PageResult<CommentVO> queryChildComment(CommentChildQueryDTO commentChildQueryDTO, Long userId);
}
