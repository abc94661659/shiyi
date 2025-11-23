package com.linshiyi.article.controller;


import com.linshiyi.article.domain.dto.ArticleCategoryCreateDTO;
import com.linshiyi.article.domain.dto.ArticleCategoryUpdateDTO;
import com.linshiyi.article.service.ArticleCategoryService;
import com.linshiyi.core.annotation.RequireLogin;
import com.linshiyi.core.entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "文章分类服务")
@AllArgsConstructor
@RequestMapping("/article/categories")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @PostMapping
    @RequireLogin
    @Operation(summary = "创建文章分类")
    public Result<String> createArticle(@RequestBody @Valid ArticleCategoryCreateDTO articleCategoryCreateDTO) {
        articleCategoryService.createArticleCategory(articleCategoryCreateDTO);
        return Result.success("创建文章分类成功");
    }

    @PutMapping("/{id}")
    @RequireLogin
    @Operation(summary = "更新文章分类")
    public Result<String> updateArticle(@RequestBody @Valid ArticleCategoryUpdateDTO articleCategoryUpdateDTO,
                                        @NotNull @PathVariable Long id) {
        articleCategoryService.updateArticleCategory(articleCategoryUpdateDTO, id);
        return Result.success("更新文章分类成功");
    }


}
