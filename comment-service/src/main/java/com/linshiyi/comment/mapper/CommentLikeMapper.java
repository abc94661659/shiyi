package com.linshiyi.comment.mapper;

import com.linshiyi.comment.domain.po.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentLikeMapper {


    CommentLike selectByCommentId(Long commentId);

    void insert(CommentLike commentLike);

    void update(CommentLike commentLike);

    Boolean isCommentLikedByUser(Long userId,@Param("commentId") Long commentId);
}
