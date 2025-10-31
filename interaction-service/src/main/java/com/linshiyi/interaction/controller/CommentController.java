package com.linshiyi.interaction.controller;

import com.linshiyi.common.common.Result;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "评论服务管理")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/createComment")
    @Operation(summary = "创建评论")
    public Result<String> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO){
        commentService.createComment(commentCreateDTO);
        return Result.success("创建评论成功");
    }


}
