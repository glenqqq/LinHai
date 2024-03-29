package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.switchWechatId.ApproveSwitchWechatIdRequest;
import com.tencent.wxcloudrun.dto.switchWechatId.CreateSwitchWechatIdRequest;
import com.tencent.wxcloudrun.dto.userCollection.CreateUserCollectionRequest;
import com.tencent.wxcloudrun.service.SwitchWechatIdService;
import com.tencent.wxcloudrun.service.UserCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SwitchWechatIdController {
    final private SwitchWechatIdService switchWechatIdService;

    final private Logger logger;

    public SwitchWechatIdController(@Autowired SwitchWechatIdService switchWechatIdService) {
        this.switchWechatIdService = switchWechatIdService;
        this.logger = LoggerFactory.getLogger(SwitchWechatIdController.class);;
    }

    @PostMapping(value = "/api/switch-wechat-management/new-switch-wechat-request")
    ApiResponse createSwitchWechatIdRequest(@RequestBody CreateSwitchWechatIdRequest request) {
        switchWechatIdService.createSwitchWechatIdRequest(request);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/switch-wechat-management/get-all-switch-wechat-id/{userId}")
    ApiResponse getAllSwitchWechatIdRequestByUserId(@PathVariable String userId) {
        return ApiResponse.ok(switchWechatIdService.getAllSwitchWechatIdRequestByUserId(userId));
    }

    @PostMapping(value = "/api/user-collection-management/approve-switch-wechat-id")
    ApiResponse approveSwitchWechatIdRequest(@RequestBody ApproveSwitchWechatIdRequest request) {
        return ApiResponse.ok(switchWechatIdService.approveSwitchWechatIdRequest(request));
    }
}
