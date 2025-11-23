package com.linshiyi.article.converter;

import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.po.Article;
import com.linshiyi.article.domain.vo.ArticleEditVO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArticleMapStruct {

    /**
     * 创建文章：DTO → PO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "viewCount", constant = "0")
    @Mapping(target = "commentCount", constant = "0")
    @Mapping(target = "isDeleted", constant = "0")
    @Mapping(target = "isTop", constant = "0")
    @Mapping(target = "auditId", ignore = true)
    @Mapping(target = "auditTime", ignore = true)
    @Mapping(target = "publishTime", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    Article toPO(ArticleCreateDTO dto);

    /**
     * 获取编辑文章信息：PO → VO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "summary", source = "summary")
    @Mapping(target = "authorId", source = "authorId")
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "coverImage", source = "coverImage")
    @Mapping(target = "authorName", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "isDraft", ignore = true)
    ArticleEditVO toArticleEditVO(Article article);

    ArticleListVO toVO(Article article);

}