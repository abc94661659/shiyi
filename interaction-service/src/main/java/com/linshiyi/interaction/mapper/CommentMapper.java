package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {


    void insert(Comment comment);

    Comment selectById(Long id);

}
