package com.linshiyi.comment.mapper;

import com.linshiyi.comment.domain.dto.CommentQueryDTO;
import com.linshiyi.comment.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {


    void insert(Comment comment);

    List<Comment> selectList(CommentQueryDTO commentQueryDTO);

    Comment selectById(Long id);
}
