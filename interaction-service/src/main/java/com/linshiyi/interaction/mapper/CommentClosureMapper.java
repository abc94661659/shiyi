package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.po.CommentClosure;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentClosureMapper {


    List<CommentClosure> selectByDescendant(Long parentId);

    void batchInsert(List<CommentClosure> newClosures);

}
