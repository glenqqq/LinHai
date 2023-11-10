package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.switchWechatId.ApproveSwitchWechatIdRequest;
import com.tencent.wxcloudrun.dto.switchWechatId.CreateSwitchWechatIdRequest;
import com.tencent.wxcloudrun.model.SwitchWechatIdPair;

import java.util.List;

public interface SwitchWechatIdService {
    void createSwitchWechatIdRequest(CreateSwitchWechatIdRequest request);
    List<SwitchWechatIdPair> getAllSwitchWechatIdRequestByUserId(String userId);
    SwitchWechatIdPair approveSwitchWechatIdRequest(ApproveSwitchWechatIdRequest request);
}
