package com.linshiyi.article.service;

import com.linshiyi.article.domain.dto.ArticleCategoryCreateDTO;
import com.linshiyi.article.domain.dto.ArticleCategoryUpdateDTO;

public interface ArticleCategoryService {

    void createArticleCategory(ArticleCategoryCreateDTO articleCategoryCreateDTO);

    void updateArticleCategory(ArticleCategoryUpdateDTO articleCategoryUpdateDTO, Long id);
}
