package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class UserMessageController {
    final Logger logger;
    final UserMessageService userMessageService;

    public UserMessageController(@Autowired UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
        this.logger = LoggerFactory.getLogger(UserMessageController.class);
    }

    @GetMapping(value = "/api/user-message-management/{userId}")
    ApiResponse getUserMessageById(@PathVariable String userId) {

        logger.info(String.format("requesting user message by id: %s", userId));

        List<UserMessage> userMessages = userMessageService.getMyUserMessage(userId);

        return ApiResponse.ok(userMessages);
    }

}
