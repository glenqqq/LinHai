package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ArticleSerice;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleSerice {
    final ArticleMapper mapper;
    final UserMapper userMapper;

    public ArticleServiceImpl(@Autowired ArticleMapper mapper,
                              @Autowired UserMapper userMapper) {
        this.mapper = mapper;
        this.userMapper = userMapper;
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
        Article retrievedArticle = mapper.getArticleById(articleId);
        User articleAuthor = userMapper.getBasicUserInfo(retrievedArticle.getAuthorId());
        retrievedArticle.setAuthorProfileUrl(articleAuthor.getProfileImageUrl());
        retrievedArticle.setAuthorName(articleAuthor.getUserName());
        return retrievedArticle;
    }

    @Override
    public List<Article> getArticleByUserId(String userId) {
        return mapper.getArticleByUserId(userId);
    }

    @Override
    public List<Article> searchArticleByTitle(String target) {
        return mapper.searchArticleByTitle(target);
    }

    @Override
    public List<Article> getRecentTenArticles(int pageNo, int pageSize,int articleType, Integer order,String title) {
        int offset = (pageNo - 1) * pageSize;
        System.out.println("articleType:"+articleType);
        List<Article> retrievedArticle = mapper.getRecentTenArticles(articleType,offset, pageSize , order,title);
//        Collections.shuffle(retrievedArticle);
        return retrievedArticle;
    }

    @Override
    public Integer getArticleNumByUserId(String userId) {
        List<Article> retrievedArticles = mapper.getArticleByUserId(userId);
        if (null == retrievedArticles) {
            return 0;
        } else {
            return retrievedArticles.size();
        }
    }

    @Override
    public void hideArticle(String articleId) {

        mapper.hideArticle(articleId);
    }

    @Override
    public void showArticle(String articleId) {
        mapper.showArticle(articleId);
    }
}
