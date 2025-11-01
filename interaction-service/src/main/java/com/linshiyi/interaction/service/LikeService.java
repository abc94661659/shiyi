package com.linshiyi.interaction.service;

import com.linshiyi.interaction.domain.dto.LikeDTO;

import java.util.List;
import java.util.Map;

public interface LikeService {


    Boolean likeComment(LikeDTO likeDTO);

    Map<Long, Boolean> batchCheckLikeStatus(Long userId, String entityType, List<Long> entityIds);
}
