package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.po.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCategoryMapper {


    void insert(ArticleCategory articleCategory);

    void update(ArticleCategory articleCategory);
}
