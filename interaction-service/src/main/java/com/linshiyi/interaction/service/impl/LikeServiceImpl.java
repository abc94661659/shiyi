package com.linshiyi.interaction.service.impl;

import com.linshiyi.interaction.domain.dto.LikeDTO;
import com.linshiyi.interaction.domain.po.Like;
import com.linshiyi.interaction.mapper.LikeMapper;
import com.linshiyi.interaction.service.LikeService;
import com.linshiyi.common.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    @Override
    public Boolean likeComment(LikeDTO likeDTO) {
        // TODO 获取用户id
        Long userId = 1L;
        Like like = likeMapper.selectByCommentIdAndUserIdAndEntityType(likeDTO.getEntityId(),userId,
                likeDTO.getEntityType());
        if (like == null) {
            like = new Like();
            like.setEntityType(likeDTO.getEntityType());
            like.setEntityId(likeDTO.getEntityId());
            // TODO 用户id
            like.setUserId(1L);
            likeMapper.insert(like);
            return true;
        }
        if (like.getIsDeleted().equals(StatusEnum.DISABLED_OR_DELETED.getCode())) {
            like.setIsDeleted(StatusEnum.NORMAL.getCode());
            likeMapper.update(like);
            return true;
        }
        like.setIsDeleted(StatusEnum.DISABLED_OR_DELETED.getCode());
        likeMapper.update(like);
        return false;
    }

    @Override
    public Map<Long, Boolean> batchCheckLikeStatus(Long userId, String entityType, List<Long> entityIds) {
        if (entityIds == null || entityIds.isEmpty()) {
            return new HashMap<>();
        }
        List<Map<String, Object>> list = likeMapper.selectBatchLikeStatus(userId, entityType, entityIds);
        Map<Long, Boolean> result = new HashMap<>();
        for (Map<String, Object> map : list) {
            Long entityId = ((Number) map.get("entityId")).longValue();
            Boolean isLiked = (Boolean) map.get("isLiked");
            result.put(entityId, isLiked);
        }
        return result;
    }
}
