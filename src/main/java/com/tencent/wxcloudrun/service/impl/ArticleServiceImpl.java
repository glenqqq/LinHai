package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.service.ArticleSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleSerice {
    final ArticleMapper mapper;

    public ArticleServiceImpl(@Autowired ArticleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String createArticle(CreateArticleRequest request) {
        final String articleId = UUID.randomUUID().toString();
        final Article article = Article.builder()
                .articleId(articleId)
                .title(request.getTitle())
                .build();
        mapper.createArticle(article);
        return articleId;
    }

    @Override
    public Article getArticleById(String articleId) {
        return mapper.getArticleById(articleId);
    }

    @Override
    public List<Article> searchArticleByTitle(String target) {return mapper.searchArticleByTitle(target);}
}
