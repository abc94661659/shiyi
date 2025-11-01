package com.linshiyi.interaction.controller;

import com.linshiyi.interaction.domain.dto.LikeDTO;
import com.linshiyi.interaction.service.LikeService;
import com.linshiyi.common.common.Result;
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
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/likeComment")
    public Result<String> likeComment(@RequestBody @Valid LikeDTO likeDTO) {
        Boolean flag = likeService.likeComment(likeDTO);
        return flag ? Result.success("点赞成功") : Result.success("取消点赞成功");
    }

}
