package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleSerice {
    String createArticle(CreateArticleRequest request);

    Article getArticleById(String articleId);

    List<Article> searchArticleByTitle(String target);

    List<Article> getRecentTenArticles();
    void hideArticle(String articleId);
    void showArticle(String articleId);
}
