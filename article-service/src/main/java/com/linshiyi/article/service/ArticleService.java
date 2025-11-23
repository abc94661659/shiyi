package com.linshiyi.article.service;

import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.vo.ArticleEditVO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.core.entity.PageResult;

public interface ArticleService {
    Long createArticle(ArticleCreateDTO articleCreateDTO, Long userId);

    void updateArticle(ArticleUpdateDTO articleUpdateDTO, Long id, Long userId);

    ArticleVO getArticleById(Long id);

    PageResult<ArticleListVO> getArticleList(ArticleQueryDTO articleQueryDTO);

    void submitArticle(Long id, Long userId);

    void withdrawArticle(Long id, Long userId);

    ArticleEditVO draftArticle(Long id, Long userId);
}
