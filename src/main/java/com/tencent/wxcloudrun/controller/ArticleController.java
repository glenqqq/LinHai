package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.service.ArticleSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ArticleController {
    final ArticleSerice articleSerice;
    final Logger logger;

    public ArticleController(@Autowired ArticleSerice articleSerice) {
        this.articleSerice = articleSerice;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    /**
     * 创建新帖子
     * @return API response json
     */
    @PostMapping(value = "/api/article-management")
    ApiResponse createArticle(@RequestBody CreateArticleRequest request) {
        logger.info("/api/article createArticle request: {}", request);
        final String articleId = articleSerice.createArticle(request);

        return ApiResponse.ok(articleId);
    }

    @GetMapping(value = "/api/article-management/{articleId}")
    ApiResponse getArticleById(@PathVariable String articleId) {
        logger.info(String.format("requesting article by id: %s", articleId));
        Article retrievedArticle = articleSerice.getArticleById(articleId);
        return ApiResponse.ok(retrievedArticle);
    }

    @GetMapping(value = "/api/article-management/search-by-tile/{target}")
    ApiResponse searchArticleByTitle(@PathVariable String target) {
        logger.info(String.format("requesting search article by title: %s", target));
        List<Article> retrievedArticle = articleSerice.searchArticleByTitle(target);
        return ApiResponse.ok(retrievedArticle);
    }

    @GetMapping(value = "/api/article-management/recent-ten-articles")
    ApiResponse getTenArticles() {
        logger.info(String.format("get recent 10 articles"));
        List<Article> retrievedArticle = articleSerice.getRecentTenArticles();
        return ApiResponse.ok(retrievedArticle);
    }

    @GetMapping(value = "/api/article-management/by-user-id/{userId}")
    ApiResponse getArticleByUserId(@PathVariable String userId) {
        logger.info(String.format("get articles for user id %s", userId));
        List<Article> retrievedArticle = articleSerice.getArticleByUserId(userId);
        return ApiResponse.ok(retrievedArticle);
    }

    @PostMapping(value = "/api/article-management/hide-article")
    ApiResponse hideArticle(@RequestBody String articleId) {
        logger.info(String.format("hide article: %s", articleId));
        articleSerice.hideArticle(articleId);
        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/article-management/show-article")
    ApiResponse showArticle(@RequestBody String articleId) {
        logger.info(String.format("show article: %s", articleId));
        articleSerice.showArticle(articleId);
        return ApiResponse.ok();
    }
}
