package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.po.ArticleContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleContentMapper {

    void insert(ArticleContent articleContent);


    ArticleContent selectLatestByArticleId(Long id);
}
