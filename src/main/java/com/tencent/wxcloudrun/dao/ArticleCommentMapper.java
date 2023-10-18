package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.ArticleComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCommentMapper {
    void createArticleComment(ArticleComment articleComment);
}
