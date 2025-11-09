package com.linshiyi.interaction.controller;

import com.github.pagehelper.PageInfo;
import com.linshiyi.core.entity.po.Result;
import com.linshiyi.interaction.domain.dto.CommentChildQueryDTO;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.dto.CommentQueryDTO;
import com.linshiyi.interaction.domain.vo.CommentPageVO;
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

    @PostMapping("/createComment")
    @Operation(summary = "创建评论")
    public Result<String> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
        commentService.createComment(commentCreateDTO);
        return Result.success("创建评论成功");
    }

    @GetMapping("/queryParentComment")
    @Operation(summary = "查询父级评论")
    public Result<CommentPageVO> queryParentComment(@Valid @ModelAttribute @ParameterObject CommentQueryDTO commentQueryDTO) {
        CommentPageVO commentPageVO = commentService.queryParentComment(commentQueryDTO);
        return Result.success("查询成功", commentPageVO);
    }

    @GetMapping("/queryChildComment")
    @Operation(summary = "查询子评论")
    public Result<PageInfo<CommentVO>> queryChildComment(@Valid @ModelAttribute @ParameterObject CommentChildQueryDTO commentChildQueryDTO) {
        PageInfo<CommentVO> commentVOList = commentService.queryChildComment(commentChildQueryDTO);
        return Result.success("查询成功", commentVOList);
    }
}
