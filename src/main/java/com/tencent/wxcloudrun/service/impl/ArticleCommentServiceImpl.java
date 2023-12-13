package com.tencent.wxcloudrun.service.impl;

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
import java.util.stream.Collectors;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    final ArticleCommentMapper mapper;
    final UserMapper userMapper;
    final UserMessageMapper userMessageMapper;

    public ArticleCommentServiceImpl(@Autowired ArticleCommentMapper mapper,
                                     @Autowired UserMapper userMapper,
                                     @Autowired UserMessageMapper userMessageMapper) {
        this.mapper = mapper;
        this.userMessageMapper = userMessageMapper;
        this.userMapper = userMapper;
    }


    @Override
    public String createArticleComment(CreateCommentRequest request) {
        final String commentId = UUID.randomUUID().toString();
        final String userMessageId = UUID.randomUUID().toString();
        ArticleComment articleComment = ArticleComment
                .builder()
                .commentId(commentId)
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .repliedCommentId(request.getRepliedCommentId())
                .createdTimestamp(System.currentTimeMillis())
                .articleId(request.getArticleId())
                .build();
        UserMessage userMessage = UserMessage.builder()
                .messageId(userMessageId)
                .receiverUserId(request.getArticleAuthorId())
                .requestingUserId(request.getAuthorId())
                .articleId(request.getArticleId())
                .messageType("REPLY")
                .isMessageRead(false)
                .createTimestamp(System.currentTimeMillis())
                .build();
        mapper.createArticleComment(articleComment);
        userMessageMapper.createUserMessage(userMessage);
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
            if (repliedCommentIdToComments.containsKey(subComment.getRepliedCommentId())) {
                repliedCommentIdToComments.get(subComment.getRepliedCommentId()).add(subComment);
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
            if (null != comment.getRepliedUserId()) {
                User user = this.userMapper.getBasicUserInfo(comment.getRepliedUserId());
                if (null == user) {continue;} //TODO:log warning
                comment.setRepliedUserName(user.getUserName());
            }
        }
    }
}
