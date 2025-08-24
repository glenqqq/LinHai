package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.ArticleCommentMapper;
import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.comments.ArticleComment;
import com.tencent.wxcloudrun.model.comments.CommentList;
import com.tencent.wxcloudrun.service.ArticleCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    final UserMapper userMapper;
    final ArticleMapper articleMapper;
    final ArticleCommentMapper articleCommentMapper;
    final ArticleCommentService articleCommentService;
    final Logger logger;

    public CommentController(UserMapper userMapper, ArticleMapper articleMapper, ArticleCommentMapper articleCommentMapper, @Autowired ArticleCommentService articleCommentService) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
        this.articleCommentMapper = articleCommentMapper;
        this.articleCommentService = articleCommentService;
        this.logger = LoggerFactory.getLogger(CommentController.class);
    }

    @PostMapping(value = "/api/article-comment-management/new-article-comment")
    ApiResponse createArticle(@RequestBody CreateCommentRequest request) throws Exception {
        logger.info("/api/new-article-comment createArticleComment request: {}", request);
        final String commentId = articleCommentService.createArticleComment(request);
        return ApiResponse.ok(commentId);
    }

    @GetMapping(value = "/api/article-comment-management/get-all-comments-for-article/{articleId}")
    ApiResponse getAllCommentsForArticle(@PathVariable String articleId) {
        logger.info("getAllCommentsForArticle article id: {}", articleId);
        final List<ArticleComment> comments = articleCommentService.getAllCommentsForArticle(articleId);
        return ApiResponse.ok(comments);
    }

    @GetMapping(value = "/api/article-comment-management/get-all-comments-for-myuserid/{userid}")
    ApiResponse getAllCommentsForMoreId(@PathVariable String userid,
                                        @RequestParam(value = "page") Integer page,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        logger.info("用户id article id: {}", userid);
        final List<ArticleComment> comments = articleCommentMapper.getCommentsForUserid(userid, page, pageSize);
        logger.info("retrieved article comments: {}", comments);
        List<CommentList> commentListList = new ArrayList<>();

        for (ArticleComment comment : comments) {
            // if comment author and article author is the same person, ignore
            if (comment.getAuthorId().equals(userid)) {
                continue;
            }

            //循环获取
            CommentList commentList = new CommentList();
            commentList.setCommentId(comment.getCommentId());

            commentList.setContent(comment.getContent());

            //拿用户信息
            User userByUserId = userMapper.getUserByUserId(comment.getAuthorId());
            logger.info("retrieved user id: {}", userByUserId);
            commentList.setAuthorId(comment.getAuthorId());
            commentList.setAuthorName(userByUserId == null ? "" : userByUserId.getUserName());
            commentList.setAuthorProfileUrl(userByUserId == null ? "" : userByUserId.getProfileImageUrl());
            commentList.setCreatedTimestamp(comment.getCreatedTimestamp());

            Article articleById = articleMapper.getArticleById(comment.getArticleId());
            commentList.setArticleId(comment.getArticleId());
            commentList.setArticleTitle(articleById.getTitle());
            commentList.setArticleImg(articleById.getImages());

            if (comment.getRootCommentId() != null) {
                commentList.setRootCommentId(comment.getRootCommentId());
                commentList.setRootCommentAuthorId(comment.getRootCommentAuthorId());

                ArticleComment commentsForCommentid = articleCommentMapper.getCommentsForCommentid(comment.getRootCommentId());
                commentList.setRootCommentContent(commentsForCommentid.getContent());
                User userByUserId1 = userMapper.getUserByUserId(comment.getRootCommentAuthorId());
                if (userByUserId1 != null) {
                    commentList.setRootAuthorName(userByUserId1.getUserName());
                    commentList.setRootAuthorProfileUrl(userByUserId1.getProfileImageUrl());
                }
            }
            commentListList.add(commentList);
        }

        return ApiResponse.ok(comments);
    }
}
