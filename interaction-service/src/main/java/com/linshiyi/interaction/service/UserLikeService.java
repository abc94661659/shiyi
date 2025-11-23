package com.linshiyi.interaction.service;

import com.linshiyi.core.enums.EntityEnum;

import java.util.List;
import java.util.Map;

public interface UserLikeService {


    Map<Long, Boolean> batchCheckLikeStatus(Long userId, String entityType, List<Long> entityIds);

    void toggleLike(EntityEnum entityType, Long entityId, Long userId);
}
