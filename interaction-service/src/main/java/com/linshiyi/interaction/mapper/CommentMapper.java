package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {


    void insert(Comment comment);

    Comment selectById(Long id);

    /**
     * 查询父级评论
     *
     * @param commentQueryDTO 查询参数
     * @return 父级评论
     */
    List<Comment> selectParentComment(CommentQueryDTO commentQueryDTO);

    /**
     * 查询父评论下的第一个子评论
     *
     * @param parentId 父评论ID
     * @return 子评论
     */
    Comment selectFirstChildComment(@Param("parentId") Long parentId);
}
