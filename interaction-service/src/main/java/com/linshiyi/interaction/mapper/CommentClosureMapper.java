package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.po.CommentClosure;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentClosureMapper {

    List<CommentClosure> selectByDescendant(Long parentId);

    void batchInsert(List<CommentClosure> newClosures);

    List<Map<String, Object>> selectTotalChildCountBatch(List<Long> parentIds);
}
