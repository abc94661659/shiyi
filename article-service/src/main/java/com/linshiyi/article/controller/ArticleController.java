package com.linshiyi.article.controller;

import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.article.service.ArticleService;
import com.linshiyi.common.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "文章服务")
@AllArgsConstructor
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
        return Result.success();
    }

    @GetMapping("/getArticle/{id}")
    @Operation(summary = "根据id获取文章")
    public Result<ArticleVO> getArticleById(@PathVariable Long id) {
        return Result.success(articleService.getArticleById(id));
    }

}
