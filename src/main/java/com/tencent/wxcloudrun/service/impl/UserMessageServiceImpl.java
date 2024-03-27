package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dao.UserMessageMapper;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class UserMessageServiceImpl implements UserMessageService {

    final UserMapper userMapper;
    final ArticleMapper articleMapper;
    final UserMessageMapper userMessageMapper;

    final Logger logger;
    @Autowired
    public UserMessageServiceImpl( UserMapper userMapper, ArticleMapper articleMapper, UserMessageMapper userMessageMapper) {
        this.userMapper = userMapper;
        this.articleMapper  = articleMapper;
        this.userMessageMapper = userMessageMapper;
        this.logger = LoggerFactory.getLogger(UserServiceImpl.class);
    }
    @Override
    public List<UserMessage> getMyUserMessage(String userId) {
        List<UserMessage> retrievedUserMessage = userMessageMapper.getMyUserMessage(userId);
        for (UserMessage userMessage : retrievedUserMessage) {
            if (null != userMessage.getArticleId()) {
                userMessage.setArticle(
                        articleMapper.getArticleById(
                                userMessage.getArticleId()
                        ));
            } else if (null != userMessage.getRequestingUserId()) {
                userMessage.setCommentingUser(
                        userMapper.getUserByUserId(
                                userMessage.getRequestingUserId()
                        ));
            }
        }
        return retrievedUserMessage;
    }

    @Override
    public void createSystemMessageForArticle(String articleId) {
        List<String> allUserIds = userMapper.getAllUserId();
        for (String userId : allUserIds) {
            UserMessage userMessage = UserMessage
                    .builder()
                    .receiverUserId(userId)
                    .articleId(articleId)
                    .build();
            userMessageMapper.createUserMessage(userMessage);
        }
    }

    @Override
    public void readMessage(String messageId) {
        log.info("read message: {}", messageId);
        userMessageMapper.readMessage(messageId);
    }
}
