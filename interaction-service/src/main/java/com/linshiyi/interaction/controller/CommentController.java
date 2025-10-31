package com.linshiyi.interaction.controller;

import com.github.pagehelper.PageInfo;
import com.linshiyi.common.common.Result;
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
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/createComment")
    @Operation(summary = "创建评论")
    public Result<String> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO){
        commentService.createComment(commentCreateDTO);
        return Result.success("创建评论成功");
    }

    @GetMapping("/queryParentComment")
    @Operation(summary = "查询父级评论")
    public Result<PageInfo<CommentVO>> queryParentComment(@Valid @ModelAttribute @ParameterObject CommentQueryDTO commentQueryDTO) {
        PageInfo<CommentVO> pageInfo = commentService.queryParentComment(commentQueryDTO);
        return Result.success(pageInfo);
    }
}
