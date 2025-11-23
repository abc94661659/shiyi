package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.po.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCategoryMapper {


    void insert(@Param("dto") ArticleCategory articleCategory);

    void update(@Param("dto") ArticleCategory articleCategory, @Param("id") Long id);
}
