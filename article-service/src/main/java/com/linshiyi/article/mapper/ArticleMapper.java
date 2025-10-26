package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.po.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {

    void insert(Article article);

    Article selectById(Long id);

    void update(Article article);
}
