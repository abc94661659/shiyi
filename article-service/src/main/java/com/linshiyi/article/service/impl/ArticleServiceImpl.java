package com.linshiyi.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.linshiyi.article.converter.ArticleMapStruct;
import com.linshiyi.article.domain.dto.ArticleCreateDTO;
import com.linshiyi.article.domain.dto.ArticleQueryDTO;
import com.linshiyi.article.domain.dto.ArticleUpdateDTO;
import com.linshiyi.article.domain.po.Article;
import com.linshiyi.article.domain.po.ArticleContent;
import com.linshiyi.article.domain.po.ArticleDraft;
import com.linshiyi.article.domain.vo.ArticleEditVO;
import com.linshiyi.article.domain.vo.ArticleListVO;
import com.linshiyi.article.domain.vo.ArticleVO;
import com.linshiyi.article.enums.ArticleStatusEnum;
import com.linshiyi.article.mapper.ArticleContentMapper;
import com.linshiyi.article.mapper.ArticleDraftMapper;
import com.linshiyi.article.mapper.ArticleMapper;
import com.linshiyi.article.service.ArticleService;
import com.linshiyi.common.exception.BusinessException;
import com.linshiyi.core.entity.PageResult;
import com.linshiyi.core.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleContentMapper articleContentMapper;
    private final ArticleMapStruct articleMapStruct;
    private final ArticleDraftMapper articleDraftMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createArticle(ArticleCreateDTO articleCreateDTO, Long userId) {
        String content = articleCreateDTO.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new BusinessException("文章内容不能为空");
        }
        // 创建文章
        Article article = articleMapStruct.toPO(articleCreateDTO);
        article.setAuthorId(userId);
        articleMapper.insert(article);
        Long id = article.getId();
        Integer status = articleCreateDTO.getStatus();
        // 判断用户是直接发布还是草稿状态
        if (ArticleStatusEnum.DRAFT.getCode().equals(status)) {
            // 草稿状态
            ArticleDraft articleDraft = new ArticleDraft();
            articleDraft.setArticleId(article.getId());
            articleDraft.setContent(content);
            articleDraft.setUpdateTime(LocalDateTime.now());
            articleDraftMapper.insert(articleDraft);
        } else if (ArticleStatusEnum.REVIEWING.getCode().equals(status)) {
            // 审核中状态
            ArticleContent articleContent = new ArticleContent();
            articleContent.setArticleId(article.getId());
            articleContent.setContent(content);
            // 初始化版本为1
            articleContent.setVersion(1);
            articleContent.setCreateTime(LocalDateTime.now());
            articleContentMapper.insert(articleContent);
        } else {
            throw new BusinessException("文章状态无效，仅支持草稿或提交审核");
        }
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleUpdateDTO dto, Long id, Long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "无权限修改文章");
        }
        Integer status = article.getStatus();
        if (ArticleStatusEnum.REVIEWING.getCode().equals(status)) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "文章正在审核中，请先撤回提交");
        }
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setCategoryId(dto.getCategoryId());
        article.setCoverImage(dto.getCoverImage());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateById(article);

        // 根据文章id查询最新的一条草稿
        ArticleDraft articleDraft = articleDraftMapper.selectByArticleId(id);
        if (articleDraft != null) {
            articleDraft.setContent(dto.getContent());
            articleDraft.setUpdateTime(LocalDateTime.now());
            articleDraftMapper.updateById(articleDraft);
        } else {
            articleDraft = new ArticleDraft();
            articleDraft.setArticleId(id);
            articleDraft.setContent(dto.getContent());
            articleDraft.setUpdateTime(LocalDateTime.now());
            articleDraftMapper.insert(articleDraft);
        }
    }

    @Override
    public ArticleVO getArticleById(Long id) {
        ArticleVO articleVO = articleMapper.selectArticleById(id);
        if (articleVO == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        return articleVO;
    }

    @Override
    public PageResult<ArticleListVO> getArticleList(ArticleQueryDTO articleQueryDTO) {
        int pageNum = articleQueryDTO.getPageNum();
        int pageSize = articleQueryDTO.getPageSize();
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;

        long total = articleMapper.selectCount(articleQueryDTO);
        if (total == 0) {
            return new PageResult<>(0L, Collections.emptyList(), pageNum, pageSize);
        }
        int offset = (pageNum - 1) * pageSize;
        List<Article> articleList = articleMapper.selectList(articleQueryDTO, offset, pageSize);
        if (articleList.isEmpty()) {
            return new PageResult<>(0L, Collections.emptyList(), pageNum, pageSize);
        }
        List<ArticleListVO> voList = articleList.stream()
                .map(articleMapStruct::toVO)
                .toList();

        return new PageResult<>(total, voList, pageNum, pageSize);
    }

    private ArticleListVO convertToVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(article, articleVO);
        return articleVO;
    }

    /**
     * 提交审核
     *
     * @param id 文章id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitArticle(Long id, Long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "无权限提交该文章");
        }
        Integer status = article.getStatus();
        if (ArticleStatusEnum.REVIEWING.getCode().equals(status)) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "文章正在审核中，请勿重复提交");
        }
        ArticleDraft draft = articleDraftMapper.selectByArticleId(id);
        if (draft == null || draft.getContent() == null || draft.getContent().trim().isEmpty()) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "文章内容不能为空，请先保存草稿");
        }
        Integer maxVersion = articleContentMapper.selectMaxVersionByArticleId(id);
        int newVersion = (maxVersion == null) ? 1 : maxVersion + 1;
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(id);
        articleContent.setContent(draft.getContent());
        articleContent.setVersion(newVersion);
        articleContent.setCreateTime(LocalDateTime.now());
        articleContentMapper.insert(articleContent);

        article.setStatus(ArticleStatusEnum.REVIEWING.getCode());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateById(article);
    }

    /**
     * 撤回提交
     *
     * @param id 文章id
     */
    @Override
    public void withdrawArticle(Long id, Long userId) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "无权限撤回");
        }
        if (!ArticleStatusEnum.REVIEWING.getCode().equals(article.getStatus())) {
            throw new BusinessException(StatusCodeEnum.BUSINESS_ERROR, "仅审核中的文章可撤回");
        }
        // 改回草稿状态
        article.setStatus(ArticleStatusEnum.DRAFT.getCode());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateById(article);
    }

    /**
     * 获取文章草稿
     *
     * @param id 文章id
     * @return 文章草稿
     */
    @Override
    public ArticleEditVO draftArticle(Long id, Long userId) {
        ArticleEditVO article = articleMapper.selectDraftById(id);
        if (article == null) {
            throw new BusinessException(StatusCodeEnum.NOT_FOUND, "文章不存在");
        }
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(StatusCodeEnum.AUTH_ERROR, "无权限修改文章");
        }
        boolean isDraft = ArticleStatusEnum.DRAFT.getCode().equals(article.getStatus());
        article.setIsDraft(isDraft);
        String content;
        if (isDraft) {
            return article;
        } else {
            // 非草稿（如审核中），可返回已提交的正式内容（如果需要）
            ArticleContent published = articleContentMapper.selectLatestByArticleId(id);
            content = published != null ? published.getContent() : "";
        }
        article.setContent(content);

        return article;
    }
}
