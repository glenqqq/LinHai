package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.comments.ArticleComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCommentMapper {
    void createArticleComment(ArticleComment articleComment);

    List<ArticleComment> getFirstLevelCommentsForArticle(@Param("articleId") String articleId);
    List<ArticleComment> getSubCommentsForArticle(@Param("articleId") String articleId);
    List<ArticleComment> getCommentsForUserid(@Param("userid") String userid,
                                              @Param("offset") int offset,
                                              @Param("pageSize") int pageSize);
    ArticleComment getCommentsForCommentid(@Param("commentId") String commentId);
}
