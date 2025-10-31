package com.linshiyi.interaction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linshiyi.common.enums.StatusCodeEnum;
import com.linshiyi.common.enums.StatusEnum;
import com.linshiyi.common.exception.BusinessException;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.po.Comment;
import com.linshiyi.interaction.domain.po.CommentClosure;
import com.linshiyi.interaction.domain.vo.CommentVO;
import com.linshiyi.interaction.mapper.CommentClosureMapper;
import com.linshiyi.interaction.mapper.CommentMapper;
import com.linshiyi.interaction.service.CommentService;
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
                throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "父级评论不存在或已删除");
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
    public PageInfo<CommentVO> queryParentComment(CommentQueryDTO commentQueryDTO) {
        PageHelper.startPage(commentQueryDTO.getPageNum(), commentQueryDTO.getPageSize());
        // 查询父级评论
        List<Comment> parentComment = commentMapper.selectParentComment(commentQueryDTO);
        PageInfo<Comment> parentCommentPage = new PageInfo<>(parentComment);
        List<CommentVO> commentVOList = parentComment.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        if (!commentVOList.isEmpty()) {
            List<Long> parentIds = commentVOList.stream()
                    .map(CommentVO::getId)
                    .toList();
            List<Map<String, Object>> childCountList = commentClosureMapper.selectTotalChildCountBatch(parentIds);
            Map<Long, Integer> childCountMap = new HashMap<>();
            for (Map<String, Object> map : childCountList) {
                Long ancestorId = ((Number) map.get("ancestor_id")).longValue();
                Integer count = ((Number) map.get("child_count")).intValue();
                childCountMap.put(ancestorId, count);
            }
            // 为每个父评论设置子评论总数和第一条子评论
            for (CommentVO commentVO : commentVOList) {
                // 设置子评论总数
                commentVO.setChildCount(childCountMap.getOrDefault(commentVO.getId(), 0));

                // 查询第一条子评论
                if (commentVO.getChildCount() > 0) {
                    Comment firstChild = commentMapper.selectFirstChildComment(commentVO.getId());
                    if (firstChild != null) {
                        // TODO
                        commentVO.setChildComment(convertToVO(firstChild));
                    }
                }
            }

        }
        PageInfo<CommentVO> resultPage = new PageInfo<>(commentVOList);
        BeanUtil.copyProperties(parentCommentPage, resultPage, "list");
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



}
