package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserMessageController {
    final Logger logger;
    final UserMessageService userMessageService;

    public UserMessageController(@Autowired UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
        this.logger = LoggerFactory.getLogger(UserMessageController.class);
    }

    @GetMapping(value = "/api/user-message-management/all-message-for-user/{userId}")
    ApiResponse getUserMessageById(@PathVariable String userId) {

        logger.info(String.format("requesting user message by id: %s", userId));

        List<UserMessage> userMessages = userMessageService.getMyUserMessage(userId);

        return ApiResponse.ok(userMessages);
    }

    @PostMapping(value = "/api/user-message-management/system-message/{articleId}")
    ApiResponse createSystemMessage(@PathVariable String articleId) {

        logger.info(String.format("create system message for article : %s", articleId));

        userMessageService.getMyUserMessage(articleId);

        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/user-message-management/message-read/{messageId}")
    ApiResponse readMessage(@PathVariable String messageId) {

        logger.info(String.format("read message : %s", messageId));

        userMessageService.readMessage(messageId);

        return ApiResponse.ok();
    }
}
