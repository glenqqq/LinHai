package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.switchWechatId.ApproveSwitchWechatIdRequest;
import com.tencent.wxcloudrun.dto.switchWechatId.CreateSwitchWechatIdRequest;
import com.tencent.wxcloudrun.service.SwitchWechatIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SwitchWechatIdController {
    final private SwitchWechatIdService switchWechatIdService;
    final private Logger logger;

    public SwitchWechatIdController(@Autowired SwitchWechatIdService switchWechatIdService) {
        this.switchWechatIdService = switchWechatIdService;
        this.logger = LoggerFactory.getLogger(SwitchWechatIdController.class);
    }

    @PostMapping(value = "/api/switch-wechat-management/new-switch-wechat-request")
    ApiResponse createSwitchWechatIdRequest(@RequestBody CreateSwitchWechatIdRequest request) throws Exception {
        switchWechatIdService.createSwitchWechatIdRequest(request);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/switch-wechat-management/get-all-switch-wechat-id/{userId}")
    ApiResponse getAllSwitchWechatIdRequestByUserId(@PathVariable String userId) {
        return ApiResponse.ok(switchWechatIdService.getAllSwitchWechatIdRequestByUserId(userId));
    }

    @GetMapping(value = "/api/switch-wechat-management/get-all-switch-wechatbysuer")
    ApiResponse getAllSwitchWechatIdRequestByUserId(@RequestParam(value = "requestingUserId") String requestingUserId
            ,@RequestParam(value = "receiverUserId") String receiverUserId
            ,@RequestParam(value = "resourceArticleId") String resourceArticleId
    ) {
        return ApiResponse.ok(switchWechatIdService.getAllSwitchWechatIdRequest(requestingUserId,receiverUserId,resourceArticleId));
    }

    @GetMapping(value = "/api/switch-wechat-management/get-all-switch-selectswitchbyrequrece")
    ApiResponse selectSwitchByRequRece(@RequestParam(value = "requestingUserId") String requestingUserId
            ,@RequestParam(value = "receiverUserId") String receiverUserId
    ) {
        return ApiResponse.ok(switchWechatIdService.selectSwitchByRequRece(requestingUserId,receiverUserId));
    }



    @GetMapping(value = "/api/switch-wechat-management/get-all-switch-by-articleId")
    ApiResponse getMySwitchWechatByArticleId(@RequestParam(value = "resourceArticleId") String resourceArticleId
    ) {
        return ApiResponse.ok(switchWechatIdService.getAllSwitchWechatIdRequest(null,null,resourceArticleId));
    }

    @PostMapping(value = "/api/user-collection-management/approve-switch-wechat-id")
    ApiResponse approveSwitchWechatIdRequest(@RequestBody ApproveSwitchWechatIdRequest request) {
        return ApiResponse.ok(switchWechatIdService.approveSwitchWechatIdRequest(request));
    }

}
