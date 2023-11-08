package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.dto.userCollection.CreateUserCollectionRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserCollection;
import com.tencent.wxcloudrun.service.UserCollectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserCollectionController {
    final private UserCollectionService userCollectionService;

    final private Logger logger;

    public UserCollectionController(UserCollectionService userCollectionService, Logger logger) {
        this.userCollectionService = userCollectionService;
        this.logger = logger;
    }

    @PostMapping(value = "/api/user-collection-management/new-user-collection")
    ApiResponse createUserCollection(@RequestBody CreateUserCollectionRequest request) {
        userCollectionService.createUserCollection(request);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/user-collection-management/all-collections-for-user/{userId}")
    ApiResponse getMyCollections(@PathVariable String userId) {
        logger.info(String.format("requesting user collections by id: %s", userId));


        List<UserCollection> collections = userCollectionService.getMyArticleCollections(userId);

        return ApiResponse.ok(collections);
    }
}
