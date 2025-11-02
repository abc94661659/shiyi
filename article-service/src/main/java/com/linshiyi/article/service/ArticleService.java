package com.linshiyi.article.service;

import com.github.pagehelper.PageInfo;
import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;

public interface ArticleService {
    void createArticle(ArticleCreateDTO articleCreateDTO);

    void updateArticle(ArticleUpdateDTO articleUpdateDTO);

    ArticleVO getArticleById(Long id);

    PageInfo<ArticleListVO> getArticleList(ArticleQueryDTO articleQueryDTO);
}
