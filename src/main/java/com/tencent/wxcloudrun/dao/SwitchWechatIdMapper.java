package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.SwitchWechatIdPair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchWechatIdMapper {
    void createSwitchWechatIdRequest(SwitchWechatIdPair request);
    List<SwitchWechatIdPair> getAllSwitchWechatIdRequestByUserId(String userId);
    List<SwitchWechatIdPair> getAllSwitchWechatIdRequest(@Param("requestingUserId") String requestingUserId,@Param("receiverUserId") String receiverUserId,@Param("resourceArticleId")String resourceArticleId);
    List<SwitchWechatIdPair> selectSwitchByRequRece(@Param("requestingUserId") String requestingUserId,@Param("receiverUserId") String receiverUserId);
    SwitchWechatIdPair getSwitchInfoBy2Ids(SwitchWechatIdPair request);
    void approveSwitchWechatIdRequest(SwitchWechatIdPair request);

}
