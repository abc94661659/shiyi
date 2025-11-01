package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.po.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LikeMapper {


    void insert(Like like);

    void update(Like like);

    Like selectByCommentIdAndUserIdAndEntityType(Long entityId, Long userId, String entityType);

    List<Map<String, Object>> selectBatchLikeStatus(@Param("userId") Long userId,
                                             @Param("entityType") String entityType,
                                             @Param("entityIds") List<Long> entityIds);
}
