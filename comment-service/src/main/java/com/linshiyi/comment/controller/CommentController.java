package com.linshiyi.comment.controller;

import com.github.pagehelper.PageInfo;
import com.linshiyi.comment.domain.dto.CommentCreateDTO;
import com.linshiyi.comment.domain.dto.CommentQueryDTO;
import com.linshiyi.comment.domain.vo.CommentVO;
import com.linshiyi.comment.service.CommentService;
import com.linshiyi.common.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/getCommentList")
    @Operation(summary = "获取评论列表")
    public Result<PageInfo<CommentVO>> getCommentList(@ParameterObject CommentQueryDTO commentQueryDTO){
        PageInfo<CommentVO> pageResultVO = commentService.getCommentList(commentQueryDTO);
        return Result.success(pageResultVO);
    }
}
