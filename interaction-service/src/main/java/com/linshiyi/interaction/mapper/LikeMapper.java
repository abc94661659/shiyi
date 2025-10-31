package com.linshiyi.interaction.mapper;

import com.linshiyi.interaction.domain.po.Like;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {


    void insert(Like like);

    void update(Like like);

    Like selectByCommentIdAndUserIdAndEntityType(Long entityId, Long userId, String entityType);
}
