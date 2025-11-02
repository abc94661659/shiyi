package com.linshiyi.article.controller;

import com.linshiyi.article.domain.dto.ArticleCategoryCreateDTO;
import com.linshiyi.article.domain.dto.ArticleCategoryUpdateDTO;
import com.linshiyi.article.service.ArticleCategoryService;
import com.linshiyi.common.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "文章分类服务")
@AllArgsConstructor
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @PostMapping("/createCategory")
    @Operation(summary = "创建文章分类")
    public Result<String> createArticle(@RequestBody @Valid ArticleCategoryCreateDTO articleCategoryCreateDTO) {
        articleCategoryService.createArticleCategory(articleCategoryCreateDTO);
        return Result.success("创建文章分类成功");
    }

    @PostMapping("/updateCategory")
    @Operation(summary = "更新文章分类")
    public Result<String> updateArticle(@RequestBody @Valid ArticleCategoryUpdateDTO articleCategoryUpdateDTO) {
        articleCategoryService.updateArticleCategory(articleCategoryUpdateDTO);
        return Result.success("更新文章分类成功");
    }
    

}
