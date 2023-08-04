package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    void createArticle(Article article);

    Article getArticleById(String articleId);

    List<Article> searchArticleByTitle(@Param("target") String target);
}
