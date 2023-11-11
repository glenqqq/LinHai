package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.comment.CreateCommentRequest;
import com.tencent.wxcloudrun.dto.followPair.CreateFollowPairRequest;
import com.tencent.wxcloudrun.model.ArticleComment;
import com.tencent.wxcloudrun.service.FollowPairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowPairController {
    final FollowPairService followPairService;
    final Logger logger;

    public FollowPairController(@Autowired FollowPairService followPairService) {
        this.followPairService = followPairService;
        this.logger = LoggerFactory.getLogger(FollowPairController.class);
    }

    @PostMapping(value = "/api/follow-pair-management/new-follow-pair")
    ApiResponse createNewFollowPair(@RequestBody CreateFollowPairRequest request) {
        return ApiResponse.ok(followPairService.createFollowPair(request));
    }

    @GetMapping(value = "/api/follow-pair-management/following-user/{userId}")
    ApiResponse getMyFollowingUser(@PathVariable String userId) {
        return ApiResponse.ok(followPairService.getMyFollowingUser(userId));
    }

    @GetMapping(value = "/api/follow-pair-management/followed-user/{userId}")
    ApiResponse getMyFollowedUser(@PathVariable String userId) {
        return ApiResponse.ok(followPairService.getMyFollowedUser(userId));
    }
}
