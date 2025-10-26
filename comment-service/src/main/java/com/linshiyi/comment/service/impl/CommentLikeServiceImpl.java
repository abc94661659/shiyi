package com.linshiyi.comment.service.impl;

import com.linshiyi.comment.domain.po.CommentLike;
import com.linshiyi.comment.mapper.CommentLikeMapper;
import com.linshiyi.comment.service.CommentLikeService;
import com.linshiyi.common.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeMapper commentLikeMapper;

    @Override
    public Boolean likeComment(Long commentId) {
        CommentLike commentLike = commentLikeMapper.selectByCommentId(commentId);
        if (commentLike == null) {
            commentLike = new CommentLike();
            commentLike.setCommentId(commentId);
            // TODO 用户id
            commentLike.setUserId(1L);
            commentLikeMapper.insert(commentLike);
            return true;
        }
        if (commentLike.getStatus().equals(StatusEnum.DISABLED_OR_DELETED.getCode())) {
            commentLike.setStatus(StatusEnum.NORMAL.getCode());
            commentLikeMapper.update(commentLike);
            return true;
        }
        commentLike.setStatus(StatusEnum.DISABLED_OR_DELETED.getCode());
        commentLikeMapper.update(commentLike);
        return false;
    }
}
