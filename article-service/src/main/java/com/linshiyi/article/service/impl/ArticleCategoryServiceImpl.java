package com.linshiyi.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.linshiyi.article.domain.dto.ArticleCategoryCreateDTO;
import com.linshiyi.article.domain.dto.ArticleCategoryUpdateDTO;
import com.linshiyi.article.domain.po.ArticleCategory;
import com.linshiyi.article.mapper.ArticleCategoryMapper;
import com.linshiyi.article.service.ArticleCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    private final ArticleCategoryMapper articleCategoryMapper;

    @Override
    public void createArticleCategory(ArticleCategoryCreateDTO articleCategoryCreateDTO) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtil.copyProperties(articleCategoryCreateDTO, articleCategory);
        // TODO 分类层级计算还未实现（使用缓存）
        articleCategory.setLevel(1);
        articleCategoryMapper.insert(articleCategory);
    }

    @Override
    public void updateArticleCategory(ArticleCategoryUpdateDTO articleCategoryUpdateDTO, Long id) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtil.copyProperties(articleCategoryUpdateDTO, articleCategory);
        // TODO 分类层级计算还未实现（使用缓存）
        articleCategory.setLevel(1);
        articleCategoryMapper.update(articleCategory, id);
    }
}
