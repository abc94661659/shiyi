package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.po.ArticleDraft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleDraftMapper {


    void insert(ArticleDraft articleDraft);

    ArticleDraft selectByArticleId(@Param("id") Long id);

    void updateById(ArticleDraft articleDraft);
}
