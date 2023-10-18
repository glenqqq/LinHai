package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.ArticleComment;

import java.util.List;

public interface ArticleCommentService {
    String createArticleComment(CreateCommentRequest request);

    List<ArticleComment> getAllCommentsForArticle(String articleId);
}
