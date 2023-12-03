package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.comments.ArticleComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCommentMapper {
    void createArticleComment(ArticleComment articleComment);

    List<ArticleComment> getAllCommentsForArticle(@Param("articleId") String articleId);
}
