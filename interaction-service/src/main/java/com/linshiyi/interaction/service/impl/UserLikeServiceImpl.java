package com.linshiyi.interaction.service.impl;


import com.linshiyi.core.enums.EntityEnum;
import com.linshiyi.interaction.mapper.UserLikeMapper;
import com.linshiyi.interaction.service.UserLikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserLikeServiceImpl implements UserLikeService {

    private final UserLikeMapper userLikeMapper;

    @Override
    public void toggleLike(EntityEnum entityType, Long entityId, Long userId) {
        boolean liked = userLikeMapper.exists(userId, entityId, entityType.getValue());
        if (liked) {
            userLikeMapper.deleteLike(userId, entityId, entityType.getValue());
        } else {
            userLikeMapper.insertLike(userId, entityId, entityType.getValue());
        }
    }

    @Override
    public Map<Long, Boolean> batchCheckLikeStatus(Long userId, String entityType, List<Long> entityIds) {
        Map<Long, Boolean> result = new HashMap<>();
        if (userId == null) {
            entityIds.forEach(id -> result.put(id, false));
            return result;
        }

        List<Map<String, Object>> records = userLikeMapper.selectBatchLikeStatus(userId, entityType, entityIds);
        for (Map<String, Object> record : records) {
            Long entityId = (Long) record.get("entityId");
            Boolean isLiked = (Boolean) record.get("isLiked");
            result.put(entityId, isLiked != null && isLiked);
        }

        // 补全未查到的实体（默认 false）
        entityIds.forEach(id -> result.putIfAbsent(id, false));
        return result;
    }
}
