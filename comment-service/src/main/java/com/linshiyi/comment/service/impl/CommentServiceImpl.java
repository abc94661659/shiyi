package com.linshiyi.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linshiyi.comment.domain.dto.CommentCreateDTO;
import com.linshiyi.comment.domain.dto.CommentQueryDTO;
import com.linshiyi.comment.domain.po.Comment;
import com.linshiyi.comment.domain.vo.CommentVO;
import com.linshiyi.comment.mapper.CommentLikeMapper;
import com.linshiyi.comment.mapper.CommentMapper;
import com.linshiyi.comment.service.CommentService;
import com.linshiyi.common.enums.StatusCodeEnum;
import com.linshiyi.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentLikeMapper commentLikeMapper;

    @Override
    public void createComment(CommentCreateDTO commentCreateDTO) {
        if (commentCreateDTO.getArticleId() == null) {
            throw new BusinessException(StatusCodeEnum.PARAM_ERROR, "文章ID不能为空");
        }
        //TODO 用户id校验


        if (commentCreateDTO.getContent() == null) {
            throw new BusinessException(StatusCodeEnum.PARAM_ERROR, "内容不能为空");
        }
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentCreateDTO, comment);
        //TODO 用户id校验
        comment.setUserId(1L);
        commentMapper.insert(comment);
    }

    @Override
    public PageInfo<CommentVO> getCommentList(CommentQueryDTO commentQueryDTO) {
        PageHelper.startPage(commentQueryDTO.getPageNum(), commentQueryDTO.getPageSize());

        // 2.执行查询
        Page<Comment> commentPage = (Page<Comment>) commentMapper.selectList(commentQueryDTO);
        // 3.转换为VO对象
        List<CommentVO> commentVOList = commentPage.stream()
                .map(comment -> {
                    CommentVO commentVO = new CommentVO();
                    BeanUtil.copyProperties(comment, commentVO);
                    // TODO 获取用户信息
                    Long userId = 1L;
                    Boolean likeStatus = commentLikeMapper.isCommentLikedByUser(userId, comment.getId());
                    commentVO.setLikeStatus(likeStatus != null ? likeStatus : false);
                    return commentVO;
                })
                .toList();
        PageInfo<CommentVO> pageInfo = new PageInfo<>(commentVOList);
        pageInfo.setTotal(commentPage.getTotal());
        pageInfo.setPageNum(commentPage.getPageNum());
        pageInfo.setPageSize(commentPage.getPageSize());
        pageInfo.setPages(commentPage.getPages());
        pageInfo.setSize(commentPage.size());
        return pageInfo;
    }


}
