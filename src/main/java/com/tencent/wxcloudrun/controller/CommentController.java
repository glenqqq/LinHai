package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.ArticleComment;
import com.tencent.wxcloudrun.service.ArticleCommentService;
import com.tencent.wxcloudrun.service.ArticleSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    final ArticleCommentService articleCommentService;
    final Logger logger;

    public CommentController(@Autowired ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
        this.logger = LoggerFactory.getLogger(CommentController.class);
    }

    @PostMapping(value = "/api/article-comment-management/new-article-comment")
    ApiResponse createArticle(@RequestBody CreateCommentRequest request) {
        logger.info("/api/new-article-comment createArticleComment request: {}", request);
        final String commentId = articleCommentService.createArticleComment(request);
        return ApiResponse.ok(commentId);
    }

    @PostMapping(value = "/api/article-comment-management/get-all-comments-for-article/{articleId}")
    ApiResponse getAllCommentsForArticle(@PathVariable String articleId) {
        logger.info("getAllCommentsForArticle article id: {}", articleId);
        final List<ArticleComment> comments = articleCommentService.getAllCommentsForArticle(articleId);
        return ApiResponse.ok(comments);
    }
}
