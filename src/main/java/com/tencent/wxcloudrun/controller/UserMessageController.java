package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.UserMessageMapper;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserMessageController {
    final Logger logger;
    final UserMessageService userMessageService;
    final UserMessageMapper userMessageMapper;

    public UserMessageController(@Autowired UserMessageService userMessageService, UserMessageMapper userMessageMapper) {
        this.userMessageService = userMessageService;
        this.userMessageMapper = userMessageMapper;
        this.logger = LoggerFactory.getLogger(UserMessageController.class);
    }

    @GetMapping(value = "/api/user-message-management/getUserMessageByRequestUserIdAndReceviceUserId")
    ApiResponse getUserMessageByRequestUserIdAndReceviceUserId(UserMessage userMessage) {
        List<UserMessage> userMessageByRequestUserIdAndReceviceUserId = userMessageMapper.getUserMessageByRequestUserIdAndReceviceUserId(userMessage.getRequestingUserId(), userMessage.getReceiverUserId());
        return ApiResponse.ok(userMessageByRequestUserIdAndReceviceUserId);
    }

    @GetMapping(value = "/api/user-message-management/all-message-for-user/{userId}")
    ApiResponse getUserMessageById(@PathVariable String userId) {
        logger.info(String.format("requesting usr message by id: %s", userId));
        List<UserMessage> userMessages = userMessageService.getMyUserMessage(userId);
        return ApiResponse.ok(userMessages);
    }

    @GetMapping(value = "/api/user-wechat-message/all-message-for-user/{userId}")
    ApiResponse getAllMyWechatMessage(@PathVariable String userId) {
        logger.info(String.format("requesting usr message by id: %s", userId));
        List<UserMessage> userMessages = userMessageMapper.getAllMyWechatMessage(userId);
        for (UserMessage userMessage : userMessages) {

        }

        return ApiResponse.ok(userMessages);
    }

    @PostMapping(value = "/api/user-message-management/system-message/{articleId}")
    ApiResponse createSystemMessage(@PathVariable String articleId) {

        logger.info(String.format("create system message for article : %s", articleId));

        userMessageService.getMyUserMessage(articleId);

        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/user-message-management/message-read")
    ApiResponse readMessage(@RequestBody String messageId) {
        logger.info(String.format("read message : %s", messageId));
        userMessageService.readMessage(messageId);
        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/user-message-management/message-unread")
    ApiResponse unreadMessage(@RequestBody String messageId) {
        logger.info(String.format("read message : %s", messageId));
        userMessageService.unreadMessage(messageId);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/user-message-management/message-read-id")
    ApiResponse readMessageById(String messageId) {
        logger.info(String.format("read message : %s", messageId));
        return ApiResponse.ok(userMessageMapper.readMessageById(messageId));
    }

    @PostMapping(value = "/api/user-message-management/message-create")
    ApiResponse createMessage(@RequestBody UserMessage userMessage) {
        userMessage.setMessageId(UUID.randomUUID().toString());
        userMessage.setMessageType("wechat");
        userMessage.setIsMessageRead(false);
        userMessage.setCreateTimestamp(System.currentTimeMillis());
        logger.info("userMessage:"+userMessage);
        userMessageMapper.createUserMessage(userMessage);
        return ApiResponse.ok();
    }
}
