package com.linshiyi.interaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserLikeMapper {


    List<Map<String, Object>> selectBatchLikeStatus(@Param("userId") Long userId,
                                                    @Param("entityType") String entityType,
                                                    @Param("entityIds") List<Long> entityIds);

    boolean exists(@Param("userId") Long userId, @Param("entityId") Long entityId, @Param("entityType") String entityType);

    void deleteLike(@Param("userId") Long userId, @Param("entityId") Long entityId, @Param("entityType") String entityType);

    void insertLike(@Param("userId") Long userId, @Param("entityId") Long entityId, @Param("entityType") String entityType);
}
