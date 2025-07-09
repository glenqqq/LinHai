package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.followPair.CreateFollowPairRequest;
import com.tencent.wxcloudrun.service.FollowPairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowPairController {
    final FollowPairService followPairService;
    final Logger logger;

    public FollowPairController(@Autowired FollowPairService followPairService) {
        this.followPairService = followPairService;
        this.logger = LoggerFactory.getLogger(FollowPairController.class);
    }

    @PostMapping(value = "/api/follow-pair-management/new-follow-pair")
    ApiResponse createNewFollowPair(@RequestBody CreateFollowPairRequest request) throws Exception {
        return ApiResponse.ok(followPairService.createFollowPair(request));
    }

    //取关
    @PostMapping(value = "/api/follow-pair-management/delete-follow-pair")
    ApiResponse deleteFollowPair(@RequestBody CreateFollowPairRequest request) {
        return ApiResponse.ok(followPairService.deleteFollowPair(request));
    }

    @GetMapping(value = "/api/follow-pair-management/following-user/{userId}")
    ApiResponse getMyFollowingUser(@PathVariable String userId) {
        return ApiResponse.ok(followPairService.getMyFollowingUser(userId));
    }

    @GetMapping(value = "/api/follow-pair-management/followed-user/{userId}")
    ApiResponse getMyFollowedUser(@PathVariable String userId) {
        return ApiResponse.ok(followPairService.getMyFollowedUser(userId));
    }

    @GetMapping(value = "/api/followed-user/{followedUser}/following-user/{followingUser}")
    ApiResponse checkIfUserFollowed(@PathVariable String followedUser, @PathVariable String followingUser) {
        logger.info(String.format("check follow pair for followed user id: %s, following user id: %s", followedUser, followingUser));
        return ApiResponse.ok(followPairService.checkIfUserFollowed(followedUser, followingUser));
    }

    @GetMapping(value = "/api/follow-pair-management/followed-user-num/{userId}")
    ApiResponse getMyFollowedUserNum(@PathVariable String userId) {
        return ApiResponse.ok(followPairService.getMyFollowedUserNum(userId));
    }
}
