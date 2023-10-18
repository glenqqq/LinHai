package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;

public interface ArticleCommentService {
    String createArticleComment(CreateCommentRequest request);
}
