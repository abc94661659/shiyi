package com.linshiyi.article.controller;


import com.github.pagehelper.PageInfo;
import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.article.service.ArticleService;
import com.linshiyi.core.entity.po.Result;
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

    @PostMapping("/createArticle")
    @Operation(summary = "创建文章")
    public Result<String> createArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO) {
        articleService.createArticle(articleCreateDTO);
        return Result.success("创建文章成功");
    }


    @PostMapping("/updateArticle")
    @Operation(summary = "更新文章")
    public Result<String> updateArticle(@RequestBody @Valid ArticleUpdateDTO articleUpdateDTO) {
        articleService.updateArticle(articleUpdateDTO);
        return Result.success("更新成功");
    }

    @GetMapping("/getArticle/{id}")
    @Operation(summary = "根据id获取文章")
    public Result<ArticleVO> getArticleById(@PathVariable Long id) {
        return Result.success("获取文章成功", articleService.getArticleById(id));
    }

    @GetMapping("/getArticleList")
    @Operation(summary = "获取文章列表")
    public Result<PageInfo<ArticleListVO>> getArticleList(@Valid @ModelAttribute @ParameterObject ArticleQueryDTO articleQueryDTO) {
        PageInfo<ArticleListVO> pageInfo = articleService.getArticleList(articleQueryDTO);
        return Result.success(pageInfo);
    }
}
