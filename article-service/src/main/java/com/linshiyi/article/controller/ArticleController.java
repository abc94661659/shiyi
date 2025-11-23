package com.linshiyi.article.controller;


import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.vo.ArticleEditVO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.article.service.ArticleService;
import com.linshiyi.common.utils.UserContext;
import com.linshiyi.core.annotation.RequireLogin;
import com.linshiyi.core.entity.PageResult;
import com.linshiyi.core.entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "文章服务")
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "新建文章")
    @RequireLogin
    public Result<Long> createArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO) {
        Long userId = UserContext.getCurrentUserId();
        Long articleId = articleService.createArticle(articleCreateDTO, userId);
        return Result.success("创建文章成功", articleId);
    }


    @PutMapping("/{id}/draft")
    @Operation(summary = "保存草稿")
    @RequireLogin
    public Result<String> updateArticle(@RequestBody @Valid ArticleUpdateDTO articleUpdateDTO, @PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        articleService.updateArticle(articleUpdateDTO, id, userId);
        return Result.success("更新成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id获取文章")
    public Result<ArticleVO> getArticleById(@PathVariable Long id) {
        return Result.success("获取文章成功", articleService.getArticleById(id));
    }

    @GetMapping
    @Operation(summary = "获取文章列表")
    public Result<PageResult<ArticleListVO>> getArticleList(@Valid @ModelAttribute @ParameterObject ArticleQueryDTO articleQueryDTO) {
        PageResult<ArticleListVO> pageInfo = articleService.getArticleList(articleQueryDTO);
        return Result.success(pageInfo);
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "提交审核")
    @RequireLogin
    public Result<String> submitArticle(@PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        articleService.submitArticle(id, userId);
        return Result.success("提交成功");
    }

    @PostMapping("/{id}/withdraw")
    @Operation(summary = "撤回审核")
    @RequireLogin
    public Result<String> withdrawArticle(@PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        articleService.withdrawArticle(id, userId);
        return Result.success("撤回成功");
    }

    @GetMapping("/{id}/draft")
    @Operation(summary = "获取文章草稿")
    @RequireLogin
    public Result<ArticleEditVO> getArticleEditInfo(@PathVariable Long id) {
        Long userId = UserContext.getCurrentUserId();
        return Result.success("获取成功", articleService.draftArticle(id, userId));
    }
}
