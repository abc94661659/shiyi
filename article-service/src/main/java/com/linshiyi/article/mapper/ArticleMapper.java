package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.po.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    void insert(Article article);

    Article selectById(Long id);

    void update(Article article);

    List<Article> selectList(ArticleQueryDTO articleQueryDTO);
}
