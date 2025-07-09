package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    void createArticle(Article article);
    void updateArticleViewById(Article article);
    void updateArticleLikeById(Article article);

    Article getArticleById(String articleId);

    List<Article> searchArticleByTitle(@Param("target") String target);

    List<Article> getRecentTenArticles(@Param("articleType")int articleType,@Param("offset") int offset, @Param("limit") int limit, @Param("order") Integer order,@Param("title") String title);
    List<Article> getRecentTenArticlesTopTitle(@Param("articleType")int articleType,@Param("offset") int offset, @Param("limit") int limit);
    List<Article> getArticleByUserId(String authorId);

    void hideArticle(String articleId);
    void showArticle(String articleId);
}
