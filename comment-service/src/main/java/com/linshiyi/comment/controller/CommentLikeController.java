package com.linshiyi.comment.controller;

import com.linshiyi.comment.service.CommentLikeService;
import com.linshiyi.common.common.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "评论点赞管理")
@AllArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("/likeComment")
    public Result<String> likeComment(@RequestParam Long commentId) {
        Boolean flag = commentLikeService.likeComment(commentId);
        return flag ? Result.success("点赞成功") : Result.success("取消点赞成功");
    }

}
