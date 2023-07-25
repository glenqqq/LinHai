package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.service.ArticleSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        logger.info("/api/article createArticle request");
        final String articleId = articleSerice.createArticle(request);

        return ApiResponse.ok(articleId);
    }

    @GetMapping(value = "/api/article-management/{articleId}")
    ApiResponse getArticleById(@PathVariable String articleId) {
        logger.info(String.format("requesting article by id: %s", articleId));
        Article retrievedArticle = articleSerice.getArticleById(articleId);
        return ApiResponse.ok(retrievedArticle);
    }
}
