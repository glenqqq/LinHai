package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.dto.user.UpdateUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    static private final String OPEN_ID = "openId";
    final Logger logger;
    final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(value = "/api/user-management/openId")
    ApiResponse gteUserByOpenid(@RequestHeader("x-wx-openid") String openId) {
        final User user = userService.getUserByOpenId(openId);
        return ApiResponse.ok(user);
    }

    @PostMapping(value = "/api/user-management/new-user")
    ApiResponse createUser(@RequestBody CreateUserRequest request, @RequestHeader("x-wx-openid") String openId) {
        request.setOpenId(openId);
        logger.info("/api/user-management/new-user createUser request: {}", request);
        final User user = userService.createUser(openId);

        return ApiResponse.ok(user);
    }

    @GetMapping(value = "/api/user-management/{userId}")
    ApiResponse getUserById(@PathVariable String userId, @RequestHeader("x-wx-openid") String openId) {
        logger.info(String.format("requesting user by id: %s", userId));
        if (OPEN_ID.equals(userId)) {
            User retrievedUser = userService.getUserByOpenId(openId);
            return ApiResponse.ok(retrievedUser);
        }

        User retrievedUser = userService.getUserByUserId(userId);

        return ApiResponse.ok(retrievedUser);
    }

    @GetMapping(value = "/api/user-management/search-by-user-name/{target}")
    ApiResponse searchArticleByTitle(@PathVariable String target) {
        logger.info(String.format("requesting search user by title: %s", target));
        List<User> retrievedUsers = userService.searchUserByUserName(target);
        return ApiResponse.ok(retrievedUsers);
    }

    @PostMapping(value = "/api/user-management/update-user")
    ApiResponse updateUserInformation(@RequestBody UpdateUserRequest request, @RequestHeader("x-wx-openid") String openId) {

        request.setOpenId(openId);
        logger.info(String.format("updateUserInformation request: %s", request));
        userService.updateUserInformation(request);
        return ApiResponse.ok(openId);
    }
}
