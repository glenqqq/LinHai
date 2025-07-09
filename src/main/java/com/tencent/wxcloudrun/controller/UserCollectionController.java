package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dto.userCollection.CreateUserCollectionRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.model.UserCollection;
import com.tencent.wxcloudrun.service.UserCollectionService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserCollectionController {
    final private UserCollectionService userCollectionService;
    final ArticleMapper articleMapper;
    final private Logger logger;

    public UserCollectionController(@Autowired UserCollectionService userCollectionService, ArticleMapper articleMapper) {
        this.userCollectionService = userCollectionService;
        this.articleMapper = articleMapper;
        this.logger = LoggerFactory.getLogger(UserCollectionController.class);;
    }

    @PostMapping(value = "/api/user-collection-management/new-user-collection")
    ApiResponse createUserCollection(@RequestBody CreateUserCollectionRequest request) {
        Article articleById = articleMapper.getArticleById(request.getArticleId());
        articleById.setLikeCount(articleById.getLikeCount()==null?0+1: articleById.getLikeCount()+ 1);
        articleMapper.updateArticleLikeById(articleById);
        userCollectionService.createUserCollection(request);
        return ApiResponse.ok();
    }

    //取收藏
    @GetMapping(value = "/api/user-collection-management/delete/user-id/{userId}/article-id/{articleId}")
    ApiResponse deleteUserCollection(@PathVariable String userId, @PathVariable String articleId) {
        logger.info(String.format("delete collection for user id: %s, article id: %s", userId, articleId));
        Article articleById = articleMapper.getArticleById(articleId);
        articleById.setLikeCount(articleById.getLikeCount()==null?0: articleById.getLikeCount()- 1);
        articleMapper.updateArticleLikeById(articleById);
        return ApiResponse.ok(userCollectionService.deleteUserCollection(userId, articleId));
    }

    @GetMapping(value = "/api/user-collection-management/all-collections-for-user/{userId}")
    ApiResponse getMyCollections(@PathVariable String userId) {
        logger.info(String.format("requesting user collections by id: %s", userId));
        List<UserCollection> collections = userCollectionService.getMyArticleCollections(userId);
        return ApiResponse.ok(collections);
    }

    @GetMapping(value = "/api/user-collection-management/user-id/{userId}/article-id/{articleId}")
    ApiResponse createUserCollection(@PathVariable String userId, @PathVariable String articleId) {
        logger.info(String.format("check collection for user id: %s, article id: %s", userId, articleId));
        return ApiResponse.ok(userCollectionService.checkIfCollected(userId, articleId));
    }
}
