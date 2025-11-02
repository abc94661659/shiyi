package com.linshiyi.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.po.Article;
import com.linshiyi.article.domain.po.ArticleContent;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.article.mapper.ArticleContentMapper;
import com.linshiyi.article.mapper.ArticleMapper;
import com.linshiyi.article.service.ArticleService;
import com.linshiyi.common.enums.StatusCodeEnum;
import com.linshiyi.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleContentMapper articleContentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createArticle(ArticleCreateDTO articleCreateDTO) {
        if (articleCreateDTO.getAuthorId() == null) {
            throw new BusinessException(StatusCodeEnum.PARAM_ERROR, "作者ID不能为空");
        }
        if (articleCreateDTO.getTitle() == null) {
            throw new BusinessException(StatusCodeEnum.PARAM_ERROR, "标题不能为空");
        }
        if (articleCreateDTO.getContent() == null) {
            throw new BusinessException(StatusCodeEnum.PARAM_ERROR, "文章内容不能为空");
        }
        Article article = new Article();
        BeanUtil.copyProperties(articleCreateDTO, article);
        articleMapper.insert(article);

        String markdownContent = articleCreateDTO.getContent();
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(article.getId());
        articleContent.setContent(markdownContent);
        articleContent.setContentVersion(1);
        articleContentMapper.insert(articleContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleUpdateDTO articleUpdateDTO) {
        Article article = articleMapper.selectById(articleUpdateDTO.getId());
        if (article == null) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "文章不存在");
        }

        // 1. 更新文章主表信息
        BeanUtil.copyProperties(articleUpdateDTO, article);

        // 2. 处理文章内容
        String newContent = articleUpdateDTO.getContent();
        if (newContent != null && !newContent.trim().isEmpty()) {
            ArticleContent articleContent = articleContentMapper.selectLatestByArticleId(article.getId());
            if (articleContent == null) {
                throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章内容不存在");
            }

            // 内容有变化才更新
            if (!newContent.equals(articleContent.getContent())) {

                ArticleContent newVersionContent = new ArticleContent();
                newVersionContent.setArticleId(article.getId());
                newVersionContent.setContent(newContent);
                newVersionContent.setContentVersion(articleContent.getContentVersion() + 1); // 版本号递增
                articleContentMapper.insert(newVersionContent);
            }
        }
        articleMapper.update(article);
    }

    @Override
    public ArticleVO getArticleById(Long id) {

        if (id == null) {
            return null;
        }
        Article article = articleMapper.selectById(id);
        ArticleContent articleContent = articleContentMapper.selectLatestByArticleId(id);
        if (article == null || articleContent == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(article, articleVO);
        articleVO.setContent(articleContent.getContent());

        return articleVO;
    }

    @Override
    public PageInfo<ArticleListVO> getArticleList(ArticleQueryDTO articleQueryDTO) {
        PageHelper.startPage(articleQueryDTO.getPageNum(), articleQueryDTO.getPageSize());
        Page<Article> articlePage = (Page<Article>) articleMapper.selectList(articleQueryDTO);
        List<ArticleListVO> articleVOList = articlePage.stream()
                .map(this::convertToVO)
                .toList();
        PageInfo<ArticleListVO> resultPage = new PageInfo<>(articleVOList);
        BeanUtil.copyProperties(new PageInfo<>(articlePage), resultPage, "list");
        return resultPage;
    }

    private ArticleListVO convertToVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(article, articleVO);
        return articleVO;
    }
}
