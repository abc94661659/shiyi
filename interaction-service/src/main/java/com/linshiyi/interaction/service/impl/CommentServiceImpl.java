package com.linshiyi.interaction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linshiyi.common.enums.StatusCodeEnum;
import com.linshiyi.common.enums.StatusEnum;
import com.linshiyi.common.exception.BusinessException;
import com.linshiyi.interaction.domain.dto.CommentChildQueryDTO;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.po.Comment;
import com.linshiyi.interaction.domain.po.CommentClosure;
import com.linshiyi.interaction.domain.vo.CommentPageVO;
import com.linshiyi.interaction.domain.vo.CommentVO;
import com.linshiyi.interaction.mapper.CommentClosureMapper;
import com.linshiyi.interaction.mapper.CommentMapper;
import com.linshiyi.interaction.service.CommentService;
import com.linshiyi.interaction.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentClosureMapper commentClosureMapper;
    private final LikeService likeService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createComment(CommentCreateDTO commentCreateDTO) {
        //TODO 用户id校验

        // 先创建出comment在维护关系
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentCreateDTO, comment);
        //TODO 用户id校验
        comment.setUserId(1L);
        Long parentId = commentCreateDTO.getParentId();
        if(parentId != null) {
            Comment parentComment = commentMapper.selectById(parentId);
            if (parentComment == null || parentComment.getIsDeleted().equals(StatusEnum.DISABLED_OR_DELETED.getCode())){
                throw new BusinessException(StatusCodeEnum.NOT_FOUND, "父级评论不存在或已删除");
            }
            if (!parentComment.getEntityType().equals(comment.getEntityType()) || !parentComment.getEntityId().equals(comment.getEntityId())) {
                throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "评论实体不匹配");
            }
        }
        commentMapper.insert(comment);
        Long childId = comment.getId();
        List<CommentClosure> parentClosures = new ArrayList<>();
        if(parentId != null) {
            parentClosures = commentClosureMapper.selectByDescendant(parentId);
            if (parentClosures.isEmpty()) {
                //TODO 如果为null手动创建
                throw new BusinessException(StatusCodeEnum.SYSTEM_ERROR, "父评论层级关系异常");
            }
        }

        List<CommentClosure> newClosures = getCommentClosures(parentClosures, childId);

        commentClosureMapper.batchInsert(newClosures);
    }

    @Override
    public CommentPageVO queryParentComment(CommentQueryDTO commentQueryDTO) {
        PageHelper.startPage(commentQueryDTO.getPageNum(), commentQueryDTO.getPageSize());
        // 查询父级评论
        List<Comment> parentComment = commentMapper.selectParentComment(commentQueryDTO);
        PageInfo<Comment> parentCommentPage = new PageInfo<>(parentComment);
        List<CommentVO> commentVOList = parentComment.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        // 计算总数
        int totalComments = (int) parentCommentPage.getTotal();
        // 批量查询父评论点赞状态
        batchIsLike("COMMENT", commentVOList);
        if (!commentVOList.isEmpty()) {
            List<Long> parentIds = commentVOList.stream()
                    .map(CommentVO::getId)
                    .toList();
            List<Map<String, Object>> childCountList = commentClosureMapper.selectTotalChildCountBatch(parentIds);
            Map<Long, Integer> childCountMap = new HashMap<>();
            int totalChildCount = 0;
            for (Map<String, Object> map : childCountList) {
                Long ancestorId = ((Number) map.get("ancestor_id")).longValue();
                int count = ((Number) map.get("child_count")).intValue();
                childCountMap.put(ancestorId, count);
                totalChildCount += count;
            }
            totalComments += totalChildCount;

            List<CommentVO> childCommentVOList = new ArrayList<>();
            // 为每个父评论设置子评论总数和第一条子评论
            for (CommentVO commentVO : commentVOList) {
                // 设置子评论总数
                commentVO.setChildCount(childCountMap.getOrDefault(commentVO.getId(), 0));

                // 查询第一条子评论
                if (commentVO.getChildCount() > 0) {
                    Comment firstChild = commentMapper.selectFirstChildComment(commentVO.getId());
                    if (firstChild != null) {
                        // TODO
                        CommentVO childCommentVO = convertToVO(firstChild);
                        commentVO.setChildComment(childCommentVO);
                        childCommentVOList.add(childCommentVO);
                    }
                }
            }
            batchIsLike("COMMENT", childCommentVOList);
        }

        PageInfo<CommentVO> resultPage = new PageInfo<>(commentVOList);
        BeanUtil.copyProperties(parentCommentPage, resultPage, "list");
        CommentPageVO commentPageVO = new CommentPageVO();
        resultPage.setTotal(totalComments);
        commentPageVO.setPageInfo(resultPage);
        commentPageVO.setTotalParentComments(parentCommentPage.getTotal());
        return commentPageVO;
    }



    @Override
    public PageInfo<CommentVO> queryChildComment(CommentChildQueryDTO commentChildQueryDTO) {
        PageHelper.startPage(commentChildQueryDTO.getPageNum(), commentChildQueryDTO.getPageSize());
        Page<Comment> childCommentPage = (Page<Comment>) commentMapper.selectChildComment(commentChildQueryDTO);
        List<CommentVO> commentVOList = childCommentPage.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        batchIsLike("COMMENT", commentVOList);
        PageInfo<CommentVO> resultPage = new PageInfo<>(commentVOList);
        BeanUtil.copyProperties(new PageInfo<>(childCommentPage), resultPage, "list");
        return resultPage;
    }

    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtil.copyProperties(comment, vo);
        return vo;
    }
    /**
     * 生成新评论的闭包关系
     */
    private static List<CommentClosure> getCommentClosures(List<CommentClosure> parentClosures, Long childId) {
        List<CommentClosure> newClosures = new ArrayList<>();
        // 遍历父评论的所有祖先，生成新评论与祖先的关系（深度+1）
        for (CommentClosure parentClosure : parentClosures) {
            CommentClosure closure = new CommentClosure();
            closure.setAncestorId(parentClosure.getAncestorId());
            closure.setDescendantId(childId);
            closure.setDepth(parentClosure.getDepth() + 1);
            newClosures.add(closure);
        }
        // 添加自身到自身的关系
        CommentClosure selfClosure = new CommentClosure();
        selfClosure.setAncestorId(childId);
        selfClosure.setDescendantId(childId);
        selfClosure.setDepth(0);
        newClosures.add(selfClosure);
        return newClosures;
    }

    private void batchIsLike(String entityType, List<CommentVO> commentVOList) {
        if (!commentVOList.isEmpty()) {
            List<Long> commentIds = commentVOList.stream()
                    .map(CommentVO::getId)
                    .collect(Collectors.toList());
            // TODO: 从上下文获取真实用户ID
            Long userId = 1L;
            Map<Long, Boolean> likeStatusMap = likeService.batchCheckLikeStatus(
                    userId,
                    entityType,
                    commentIds
            );

            // 设置点赞状态
            commentVOList.forEach(vo ->
                    vo.setIsLike(likeStatusMap.getOrDefault(vo.getId(), false))
            );
        }
    }
}
