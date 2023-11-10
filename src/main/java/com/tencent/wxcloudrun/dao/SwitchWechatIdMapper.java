package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.SwitchWechatIdPair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SwitchWechatIdMapper {
    void createSwitchWechatIdRequest(SwitchWechatIdPair request);
    List<SwitchWechatIdPair> getAllSwitchWechatIdRequestByUserId(String userId);
    SwitchWechatIdPair getSwitchInfoBy2Ids(SwitchWechatIdPair request);
    void approveSwitchWechatIdRequest(SwitchWechatIdPair request);

}
