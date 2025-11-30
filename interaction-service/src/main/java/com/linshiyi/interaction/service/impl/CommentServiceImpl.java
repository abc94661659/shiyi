package com.linshiyi.interaction.service.impl;

import com.linshiyi.client.user.UserClient;
import com.linshiyi.common.exception.BusinessException;
import com.linshiyi.core.entity.PageResult;
import com.linshiyi.core.entity.vo.UserBriefVO;
import com.linshiyi.core.enums.StatusCodeEnum;
import com.linshiyi.core.enums.StatusEnum;
import com.linshiyi.interaction.converter.CommentMapStruct;
import com.linshiyi.interaction.domain.dto.CommentChildQueryDTO;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.po.Comment;
import com.linshiyi.interaction.domain.po.CommentClosure;
import com.linshiyi.interaction.domain.vo.CommentVO;
import com.linshiyi.interaction.mapper.CommentClosureMapper;
import com.linshiyi.interaction.mapper.CommentMapper;
import com.linshiyi.interaction.service.CommentService;
import com.linshiyi.interaction.service.UserLikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentClosureMapper commentClosureMapper;
    private final UserLikeService userLikeService;
    private final CommentMapStruct commentConverter;
    private final UserClient userClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createComment(CommentCreateDTO commentCreateDTO, Long userId) {
        // 先创建出comment再维护关系
        Comment comment = commentConverter.toPO(commentCreateDTO);
        comment.setUserId(userId);
        Long parentId = commentCreateDTO.getParentId();
        if (parentId != null) {
            Comment parentComment = commentMapper.selectById(parentId);
            if (parentComment == null || parentComment.getIsDeleted().equals(StatusEnum.DELETED.getCode())) {
                throw new BusinessException(StatusCodeEnum.NOT_FOUND, "父级评论不存在或已删除");
            }
            if (!parentComment.getEntityType().equals(comment.getEntityType()) || !parentComment.getEntityId().equals(comment.getEntityId())) {
                throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "评论实体不匹配");
            }
        }
        commentMapper.insert(comment);
        Long childId = comment.getId();
        List<CommentClosure> parentClosures = new ArrayList<>();
        if (parentId != null) {
            parentClosures = commentClosureMapper.selectByDescendant(parentId);
            if (parentClosures.isEmpty()) {
                // TODO 如果为null手动创建
                throw new BusinessException(StatusCodeEnum.SYSTEM_ERROR, "父评论层级关系异常");
            }
        }

        List<CommentClosure> newClosures = getCommentClosures(parentClosures, childId);

        commentClosureMapper.batchInsert(newClosures);
    }

    @Override
    public PageResult<CommentVO> queryParentComment(CommentQueryDTO commentQueryDTO, Long userId) {
        int pageNum = commentQueryDTO.getPageNum();
        int pageSize = commentQueryDTO.getPageSize();
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 20;
        int offset = Math.max(0, (pageNum - 1) * pageSize);
        long totalParent = commentMapper.countParentComment(commentQueryDTO);
        List<CommentVO> voList = new ArrayList<>();

        if (totalParent > 0) {
            List<Comment> parentList = commentMapper.selectParentComment(commentQueryDTO, offset, pageSize);
            voList = parentList.stream()
                    .map(commentConverter::toVO)
                    .toList();
            Set<Long> userIds = new HashSet<>();
            List<CommentVO> allComments = new ArrayList<>(voList);
            List<CommentVO> childVOs = new ArrayList<>();
            List<Long> parentIds = voList.stream().map(CommentVO::getId).toList();
            List<Map<String, Object>> childCounts = commentClosureMapper.selectTotalChildCountBatch(parentIds);
            Map<Long, Integer> childCountMap = new HashMap<>();
            for (Map<String, Object> row : childCounts) {
                Long id = ((Number) row.get("ancestor_id")).longValue();
                int count = ((Number) row.get("child_count")).intValue();
                childCountMap.put(id, Math.max(0, count - 1));
            }
            List<Comment> topChildComments = Collections.emptyList();
            if (!parentIds.isEmpty()) {
                topChildComments = commentMapper.selectTopNChildCommentsByParentIds(parentIds, 2);
            }
            Map<Long, List<CommentVO>> groupedChildComments = new HashMap<>();
            for (Comment child : topChildComments) {
                CommentVO childVO = commentConverter.toVO(child);
                groupedChildComments.computeIfAbsent(child.getParentId(), k -> new ArrayList<>()).add(childVO);
                childVOs.add(childVO);
                userIds.add(childVO.getUserId());
                allComments.add(childVO);
            }
            for (CommentVO vo : voList) {
                Long parentId = vo.getId();
                userIds.add(vo.getUserId());

                int childCount = childCountMap.getOrDefault(parentId, 0);
                vo.setChildCount(childCount);


                vo.setChildComments(groupedChildComments.getOrDefault(parentId, Collections.emptyList()));
            }
            fillUserBriefInfo(allComments);
            List<CommentVO> allCommentVOsForLike = new ArrayList<>(voList);
            allCommentVOsForLike.addAll(childVOs);
            batchIsLike("COMMENT", allCommentVOsForLike, userId);
        }

        return new PageResult<>(totalParent, voList, pageNum, pageSize);
    }

    @Override
    public PageResult<CommentVO> queryChildComment(CommentChildQueryDTO dto, Long userId) {
        Long parentId = dto.getParentId();
        int pageNum = dto.getPageNum();
        int pageSize = dto.getPageSize();
        if (parentId == null) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "parentId 不能为空");
        }
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 20;
        long total = commentMapper.countChildComment(parentId);
        if (total == 0) {
            return new PageResult<>(0L, Collections.emptyList(), pageNum, pageSize);
        }
        int offset = (pageNum - 1) * pageSize;
        List<CommentVO> childComments = commentMapper.selectChildCommentWithPaging(parentId, offset, pageSize);
        if (childComments.isEmpty()) {
            return new PageResult<>(total, Collections.emptyList(), pageNum, pageSize);
        }

        fillUserBriefInfo(childComments);

        batchIsLike("COMMENT", childComments, userId);

        return new PageResult<>(total, childComments, pageNum, pageSize);
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

    private void batchIsLike(String entityType, List<CommentVO> commentVOList, Long userId) {
        if (commentVOList.isEmpty()) {
            return;
        }

        List<Long> commentIds = commentVOList.stream()
                .map(CommentVO::getId)
                .collect(Collectors.toList());

        // userId 为 null 表示未登录用户 → 批量检查时全部视为未点赞
        Map<Long, Boolean> likeStatusMap = userLikeService.batchCheckLikeStatus(userId, entityType, commentIds);

        commentVOList.forEach(vo ->
                vo.setIsLike(likeStatusMap.getOrDefault(vo.getId(), false))
        );
    }

    /**
     * 批量填充评论列表中的用户昵称和头像
     *
     * @param commentVOs 需要填充用户信息的所有 CommentVO（包括父评论及其首条子评论等）
     */
    private void fillUserBriefInfo(List<CommentVO> commentVOs) {
        if (commentVOs == null || commentVOs.isEmpty()) {
            return;
        }

        Set<Long> userIds = new HashSet<>();
        for (CommentVO vo : commentVOs) {
            if (vo.getUserId() != null) {
                userIds.add(vo.getUserId());
            }
            // 子评论/回复目标的作者ID
            if (vo.getReplyToUserId() != null) {
                userIds.add(vo.getReplyToUserId());
            }
        }
        Map<Long, UserBriefVO> userBriefMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            try {
                List<UserBriefVO> briefs = userClient.getUserBriefs(userIds);
                userBriefMap = briefs.stream()
                        .collect(Collectors.toMap(UserBriefVO::getId, Function.identity(), (u1, u2) -> u1));
            } catch (Exception e) {
                log.warn("Failed to fetch user briefs for userIds: {}, error: {}", userIds, e.getMessage());
                // fallback: 为每个用户生成默认信息
                for (Long id : userIds) {
                    UserBriefVO defaultBrief = new UserBriefVO();
                    defaultBrief.setId(id);
                    defaultBrief.setNickname("未知用户");
                    defaultBrief.setAvatar("https://example.com/default-avatar.png");
                    userBriefMap.put(id, defaultBrief);
                }
            }
        }

        // 填充 nickName 和 avatar
        for (CommentVO comment : commentVOs) {
            if (comment.getUserId() == null) {
                comment.setNickName("未知用户");
                comment.setAvatar("https://example.com/default-avatar.png");
                continue;
            }
            UserBriefVO brief = userBriefMap.get(comment.getUserId());
            if (brief != null) {
                comment.setNickName(brief.getNickname());
                comment.setAvatar(brief.getAvatar());
            } else {
                comment.setNickName("未知用户");
                comment.setAvatar("https://example.com/default-avatar.png");
            }
            if (comment.getReplyToUserId() != null) {
                UserBriefVO replyBrief = userBriefMap.get(comment.getReplyToUserId());
                if (replyBrief != null) {
                    comment.setReplyToNickName(replyBrief.getNickname());
                } else {
                    comment.setReplyToNickName("未知用户");
                }
            }
        }
    }
}
