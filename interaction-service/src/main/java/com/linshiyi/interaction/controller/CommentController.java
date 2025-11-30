package com.linshiyi.interaction.controller;

import com.linshiyi.common.utils.UserContext;
import com.linshiyi.core.annotation.LogOperation;
import com.linshiyi.core.annotation.RequireLogin;
import com.linshiyi.core.entity.PageResult;
import com.linshiyi.core.entity.Result;
import com.linshiyi.interaction.domain.dto.CommentChildQueryDTO;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.vo.CommentVO;
import com.linshiyi.interaction.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "评论服务管理")
@AllArgsConstructor
@RequestMapping("/interaction/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "创建评论")
    @RequireLogin
    @LogOperation(resourceType = "评论", operationType = "创建评论",
            desc = "创建实体类型 #{#commentCreateDTO.entityType} ID为 #{#commentCreateDTO.entityId} 的评论")
    public Result<String> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
        Long userId = UserContext.getCurrentUserId();
        commentService.createComment(commentCreateDTO, userId);
        return Result.success("创建评论成功");
    }

    @GetMapping("/parent")
    @Operation(summary = "查询父级评论")
    public Result<PageResult<CommentVO>> queryParentComment(@Valid @ModelAttribute @ParameterObject CommentQueryDTO queryDTO) {
        Long userId = UserContext.getCurrentUserId();
        PageResult<CommentVO> result = commentService.queryParentComment(queryDTO, userId);
        return Result.success("查询成功", result);
    }

    @GetMapping("/child")
    @Operation(summary = "查询子评论")
    @RequireLogin
    @LogOperation(resourceType = "评论", operationType = "查询子评论",
            desc = "查询父评论ID为 #{#commentChildQueryDTO.parentId} 的子评论")
    public Result<PageResult<CommentVO>> queryChildComment(@Valid @ModelAttribute @ParameterObject CommentChildQueryDTO commentChildQueryDTO) {
        Long userId = UserContext.getCurrentUserId();
        PageResult<CommentVO> result = commentService.queryChildComment(commentChildQueryDTO, userId);
        return Result.success("查询成功", result);
    }
}
