package com.linshiyi.article.mapper;

import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.po.Article;
import com.linshiyi.article.domain.vo.ArticleEditVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    long insert(Article article);

    Article selectById(Long id);

    void updateById(Article article);

    List<Article> selectList(@Param("dto") ArticleQueryDTO articleQueryDTO,
                             @Param("offset") Integer offset,
                             @Param("pageSize") Integer pageSize);

    ArticleVO selectArticleById(Long id);

    ArticleEditVO selectDraftById(Long id);

    long selectCount(ArticleQueryDTO articleQueryDTO);
}
