package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;

public interface ArticleSerice {
    String createArticle(CreateArticleRequest request);

    Article getArticleById(String articleId);
}
