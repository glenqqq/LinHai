package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ArticleCommentMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.ArticleComment;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    final ArticleCommentMapper mapper;
    final UserMapper userMapper;

    public ArticleCommentServiceImpl(@Autowired ArticleCommentMapper mapper, @Autowired UserMapper userMapper) {
        this.mapper = mapper;
        this.userMapper = userMapper;
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
                .articleId(request.getArticleId())
                .build();
        mapper.createArticleComment(articleComment);
        return commentId;
    }

    @Override
    public List<ArticleComment> getAllCommentsForArticle(String articleId) {
        List<ArticleComment> retrievedComments = mapper.getAllCommentsForArticle(articleId);
        retrievedComments.forEach(articleComment -> {
            final User authorBasicInfo = this.userMapper.getBasicUserInfo(articleComment.getAuthorId());
            articleComment.setAuthorName(authorBasicInfo.getUserName());
            articleComment.setAuthorProfileUrl(authorBasicInfo.getProfileImageUrl());
            if (null != articleComment.getRepliedUserId()) {

                User user = this.userMapper.getBasicUserInfo(articleComment.getRepliedUserId());
                if (null != user) {
                    articleComment.setRepliedUserName(user.getUserName());
                }
            }
        });
        Map<String, List<ArticleComment>> repliedCommentIdToComments = new HashMap<>();

        collectSubComments(retrievedComments, repliedCommentIdToComments);

        List<ArticleComment> formedComments = new ArrayList<>();
        for (ArticleComment comment : retrievedComments) {
            if (repliedCommentIdToComments.containsKey(comment.getRepliedCommentId())) {
                comment.setSubComments(repliedCommentIdToComments.get(comment.getRepliedCommentId()));
                formedComments.add(comment);
            }
        }
        return formedComments;
    }

    private void collectSubComments(List<ArticleComment> comments, Map<String, List<ArticleComment>> repliedCommentIdToComments) {
        for (ArticleComment comment : comments) {
            if (!repliedCommentIdToComments.containsKey(comment.getRepliedCommentId())) {
                repliedCommentIdToComments.put(comment.getRepliedCommentId(), new ArrayList<>());
            }
            repliedCommentIdToComments.get(comment.getRepliedCommentId()).add(comment);
        }
    }
}
