package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.controller.CommentController;
import com.tencent.wxcloudrun.dao.ArticleCommentMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dao.UserMessageMapper;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.model.comments.ArticleComment;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.comments.SubComments;
import com.tencent.wxcloudrun.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    final ArticleCommentMapper mapper;
    final UserMapper userMapper;
    final UserMessageMapper userMessageMapper;
    final Logger logger;

    public ArticleCommentServiceImpl(@Autowired ArticleCommentMapper mapper,
                                     @Autowired UserMapper userMapper,
                                     @Autowired UserMessageMapper userMessageMapper) {
        this.mapper = mapper;
        this.userMessageMapper = userMessageMapper;
        this.userMapper = userMapper;
        this.logger = LoggerFactory.getLogger(CommentController.class);
    }


    @Override
    public String createArticleComment(CreateCommentRequest request) {
        final String commentId = UUID.randomUUID().toString();
        final String toArticleAuthorMessageId = UUID.randomUUID().toString();


        ArticleComment articleComment = ArticleComment
                .builder()
                .commentId(commentId)
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .articleId(request.getArticleId())
                .articleAuthorId(request.getArticleAuthorId())
                .rootCommentId(request.getRootCommentId())
                .rootCommentAuthorId(request.getRootCommentAuthorId())
                .leaveCommentId(request.getLeaveCommentId())
                .leaveCommentAuthorId(request.getLeaveCommentAuthorId())
                .createdTimestamp(System.currentTimeMillis())
                .build();
        logger.info("articleComment in service level: {}", articleComment);
        mapper.createArticleComment(articleComment);
        UserMessage articleAuthorMessage = UserMessage.builder()
                .messageId(toArticleAuthorMessageId)
                .receiverUserId(request.getArticleAuthorId())
                .requestingUserId(request.getAuthorId())
                .articleId(request.getArticleId())
                .messageType("REPLY")
                .isMessageRead(false)
                .createTimestamp(System.currentTimeMillis())
                .build();
        userMessageMapper.createUserMessage(articleAuthorMessage);

        if (null != request.getRootCommentId()) {
            final String toRootArticleMessageId = UUID.randomUUID().toString();
            UserMessage levelOneCommentMessage = UserMessage.builder()
                    .messageId(toRootArticleMessageId)
                    .receiverUserId(request.getRootCommentAuthorId())
                    .requestingUserId(request.getAuthorId())
                    .articleId(request.getArticleId())
                    .messageType("REPLY")
                    .isMessageRead(false)
                    .createTimestamp(System.currentTimeMillis())
                    .build();
            userMessageMapper.createUserMessage(levelOneCommentMessage);
        }

        if (null != request.getLeaveCommentId()) {
            final String toLeafArticleMessageId = UUID.randomUUID().toString();
            UserMessage levelOneCommentMessage = UserMessage.builder()
                    .messageId(toLeafArticleMessageId)
                    .receiverUserId(request.getLeaveCommentAuthorId())
                    .requestingUserId(request.getAuthorId())
                    .articleId(request.getArticleId())
                    .messageType("REPLY")
                    .isMessageRead(false)
                    .createTimestamp(System.currentTimeMillis())
                    .build();
            userMessageMapper.createUserMessage(levelOneCommentMessage);
        }
        return commentId;
    }

    @Override
    public List<ArticleComment> getAllCommentsForArticle(String articleId) {
        List<ArticleComment> retrievedComments = mapper.getFirstLevelCommentsForArticle(articleId);
        List<SubComments> subComments = mapper.getSubCommentsForArticle(articleId)
                .stream()
                .map(SubComments::new)
                .collect(Collectors.toList());
        getUserInfoForComments(retrievedComments);
        getUserInfoForComments(subComments);

        Map<String, List<SubComments>> repliedCommentIdToComments = new HashMap<>();

        for (ArticleComment articleComment : retrievedComments) {
            repliedCommentIdToComments.put(articleComment.getCommentId(), new ArrayList<>());
        }

        for (SubComments subComment : subComments) {
            if (subComment.getRootCommentAuthorId() != null) {
                User userByUserId = userMapper.getUserByUserId(subComment.getRootCommentAuthorId());
                subComment.setRootCommentAuthorName(userByUserId.getUserName());
            }
            if (repliedCommentIdToComments.containsKey(subComment.getRootCommentId())) {
                repliedCommentIdToComments.get(subComment.getRootCommentId()).add(subComment);
            }
            //TODO: add else warning
        }

        List<ArticleComment> formedComments = new ArrayList<>();
        for (ArticleComment comment : retrievedComments) {
            if (repliedCommentIdToComments.containsKey(comment.getCommentId())) {
                comment.setSubComments(repliedCommentIdToComments.get(comment.getCommentId()));
                formedComments.add(comment);
            }
        }
        return formedComments;
    }

    private void getUserInfoForComments(List<? extends ArticleComment> comments) {
        for (ArticleComment comment : comments) {
            final User authorBasicInfo = this.userMapper.getBasicUserInfo(comment.getAuthorId());
            if (null == authorBasicInfo) {continue;} //TODO:log warning
            comment.setAuthorName(authorBasicInfo.getUserName());
            comment.setAuthorProfileUrl(authorBasicInfo.getProfileImageUrl());
            if (null != comment.getLeaveCommentId()) {
                User user = this.userMapper.getBasicUserInfo(comment.getLeaveCommentId());
                if (null == user) {continue;} //TODO:log warning
                comment.setLeaveCommentAuthorName(user.getUserName());
            }
        }
    }
}
