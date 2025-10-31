package com.linshiyi.interaction.service;

import com.linshiyi.interaction.domain.dto.CommentLikeDTO;

public interface LikeService {


    Boolean likeComment(CommentLikeDTO commentLikeDTO);
}
