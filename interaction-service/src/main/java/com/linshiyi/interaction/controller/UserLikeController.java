package com.linshiyi.interaction.controller;

import com.linshiyi.common.utils.UserContext;
import com.linshiyi.core.annotation.RequireLogin;
import com.linshiyi.core.entity.Result;
import com.linshiyi.interaction.domain.dto.UserLikeDTO;
import com.linshiyi.interaction.service.UserLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "评论点赞管理")
@AllArgsConstructor
@RequestMapping("/interaction/like")
public class UserLikeController {
    private final UserLikeService userLikeService;

    @RequireLogin
    @PostMapping()
    @Operation(summary = "点赞/取消点赞")
    public Result<String> toggleLike(@RequestBody @Valid UserLikeDTO userLikeDTO) {
        Long userId = UserContext.getCurrentUserId();
        userLikeService.toggleLike(userLikeDTO.getEntityType(), userLikeDTO.getEntityId(), userId);
        return Result.success("操作成功");
    }


}
