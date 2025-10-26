package com.linshiyi.comment.service;

import com.github.pagehelper.PageInfo;
import com.linshiyi.comment.domain.dto.CommentCreateDTO;
import com.linshiyi.comment.domain.dto.CommentQueryDTO;
import com.linshiyi.comment.domain.vo.CommentVO;

public interface CommentService {

    void createComment(CommentCreateDTO commentCreateDTO);

    PageInfo<CommentVO> getCommentList(CommentQueryDTO commentQueryDTO);

}
