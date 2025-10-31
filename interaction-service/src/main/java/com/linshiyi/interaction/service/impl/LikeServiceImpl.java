package com.linshiyi.interaction.service.impl;

import com.linshiyi.interaction.domain.dto.CommentLikeDTO;
import com.linshiyi.interaction.domain.po.Like;
import com.linshiyi.interaction.mapper.LikeMapper;
import com.linshiyi.interaction.service.LikeService;
import com.linshiyi.common.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    @Override
    public Boolean likeComment(CommentLikeDTO commentLikeDTO) {
        // TODO 获取用户id
        Long userId = 1L;
        Like like = likeMapper.selectByCommentIdAndUserIdAndEntityType(commentLikeDTO.getEntityId(),userId,
                commentLikeDTO.getEntityType());
        if (like == null) {
            like = new Like();
            like.setEntityType(commentLikeDTO.getEntityType());
            like.setEntityId(commentLikeDTO.getEntityId());
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
}
