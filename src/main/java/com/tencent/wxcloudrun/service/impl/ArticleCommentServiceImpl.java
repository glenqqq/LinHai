package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ArticleCommentMapper;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.ArticleComment;
import com.tencent.wxcloudrun.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    final ArticleCommentMapper mapper;

    public ArticleCommentServiceImpl(@Autowired ArticleCommentMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String createArticleComment(CreateCommentRequest request) {
        final String commentId = UUID.randomUUID().toString();
        ArticleComment articleComment = ArticleComment
                .builder()
                .commentId(commentId)
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .repliedCommentId(request.getRepliedCommentId())
                .createdTimestamp(System.currentTimeMillis())
                .build();
        mapper.createArticleComment(articleComment);
        return commentId;
    }
}
