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
                .articleType(request.getArticleType())
                .merchantCategory(request.getMerchantCategory())
                .images(request.getImages())
                .content(request.getContent())
                .price(request.getPrice())
                .isDeliveryCovered(request.getIsDeliveryCovered())
                .deliveryMethod(request.getDeliveryMethod())
                .location(request.getLocation())
                .isNeedHelp(request.getIsNeedHelp())
                .createTimestamp(request.getCreateTimestamp())
                .updateTimestamp(request.getUpdateTimestamp())
                .authorName(request.getAuthorName())
                .authorId(request.getAuthorId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .likeCount(request.getLikeCount())
                .viewCount(request.getViewCount())
                .isStickTop(request.getIsStickTop())
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
    public List<Article> searchArticleByTitle(String target) {
        return null;
    }

    @Override
    public List<Article> getRecentTenArticles() {
        return mapper.getRecentTenArticles();
    }
}
