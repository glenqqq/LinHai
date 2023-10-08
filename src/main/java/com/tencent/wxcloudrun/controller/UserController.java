package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    final Logger logger;
    final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(value = "/api/user-management/new-user")
    ApiResponse createArticle(@RequestBody CreateUserRequest request) {
        logger.info("/api/user-management/new-user createUser request: {}", request);
        final String userId = userService.createUser(request);

        return ApiResponse.ok(userId);
    }

    @GetMapping(value = "/api/user-management/{userId}")
    ApiResponse getArticleById(@PathVariable String userId) {
        logger.info(String.format("requesting user by id: %s", userId));
        User retrievedUser = userService.getUserByUserId(userId);
        return ApiResponse.ok(retrievedUser);
    }

    @GetMapping(value = "/api/user-management/search-by-user-name/{target}")
    ApiResponse searchArticleByTitle(@PathVariable String target) {
        logger.info(String.format("requesting search user by title: %s", target));
        List<User> retrievedUsers = userService.searchUserByUserName(target);
        return ApiResponse.ok(retrievedUsers);
    }
}
