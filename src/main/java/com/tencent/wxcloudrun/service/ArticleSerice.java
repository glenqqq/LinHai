package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;

public interface ArticleSerice {
    String createArticle(CreateArticleRequest request);
}
