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
    List<Comment> selectParentComment(@Param("dto") CommentQueryDTO commentQueryDTO,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    /**
     * 查询父评论下的第一个子评论
     *
     * @param parentId 父评论ID
     * @return 子评论
     */
    Comment selectFirstChildComment(@Param("parentId") Long parentId);


    /**
     * 查询父级评论总数
     *
     * @param commentQueryDTO 查询参数
     * @return 父级评论总数
     */
    long countParentComment(@Param("dto") CommentQueryDTO commentQueryDTO);

    /**
     * 查询子评论总数
     *
     * @param parentId 父评论ID
     * @return 子评论总数
     */
    long countChildComment(Long parentId);

    /**
     * 查询子评论
     *
     * @param parentId 父评论ID
     * @param offset   页码
     * @param pageSize 每页数量
     * @return 子评论
     */
    List<Comment> selectChildCommentWithPaging(@Param("parentId") Long parentId,
                                               @Param("offset") int offset,
                                               @Param("pageSize") int pageSize);
}
