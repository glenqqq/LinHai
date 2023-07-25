package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    void createArticle(Article article);

    Article getArticleById(String articleId);
}
