package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.SwitchWechatIdMapper;
import com.tencent.wxcloudrun.dto.switchWechatId.ApproveSwitchWechatIdRequest;
import com.tencent.wxcloudrun.dto.switchWechatId.CreateSwitchWechatIdRequest;
import com.tencent.wxcloudrun.model.SwitchWechatIdPair;
import com.tencent.wxcloudrun.service.SwitchWechatIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwitchWechatIdServiceImpl implements SwitchWechatIdService {

    static private final String APPROVED = "APPROVED";

    static private final String PENDING = "PENDING";

    static private final String REJECTED = "REJECTED";

    private final SwitchWechatIdMapper mapper;

    public SwitchWechatIdServiceImpl(@Autowired SwitchWechatIdMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void createSwitchWechatIdRequest(CreateSwitchWechatIdRequest request) {
        SwitchWechatIdPair pair = SwitchWechatIdPair.builder()
                .requesterUserId(request.getRequesterUserId())
                .requesterUserName(request.getRequesterUserName())
                .requesterUserWechatId(request.getRequesterUserWechatId())
                .requesterUserProfileUrl(request.getRequesterUserProfileUrl())
                .receiverUserLocation(request.getReceiverUserLocation())
                .receiverUserLocationId(request.getReceiverUserLocationId())

                .receiverUserId(request.getReceiverUserId())
                .receiverUserName(request.getReceiverUserName())
                .receiverUserWechatId(request.getReceiverUserWechatId())
                .receiverUserProfileUrl(request.getReceiverUserProfileUrl())
                .receiverUserLocation(request.getReceiverUserLocation())
                .receiverUserLocationId(request.getReceiverUserLocationId())

                .resourceArticleId(request.getResourceArticleId())
                .resourceArticleTitle(request.getResourceArticleTitle())
                .resourceArticleFirstPicUrl(request.getResourceArticleFirstPicUrl())

                .creationTimestamp(System.currentTimeMillis())
                .lastUpdatedTimestamp(System.currentTimeMillis())
                .approvingStatus(PENDING)
                .build();

        mapper.createSwitchWechatIdRequest(pair);


    }

    @Override
    public List<SwitchWechatIdPair> getAllSwitchWechatIdRequestByUserId(String userId) {
        return mapper.getAllSwitchWechatIdRequestByUserId(userId);
    }

    @Override
    public List<SwitchWechatIdPair> getAllSwitchWechatIdRequest(String requestingUserId, String receiverUserId,String resourceArticleId) {
        return mapper.getAllSwitchWechatIdRequest(requestingUserId,receiverUserId,resourceArticleId);
    }
    @Override
    public List<SwitchWechatIdPair> selectSwitchByRequRece(String requestingUserId, String receiverUserId) {
        return mapper.selectSwitchByRequRece(requestingUserId,receiverUserId);
    }

    @Override
    public SwitchWechatIdPair approveSwitchWechatIdRequest(ApproveSwitchWechatIdRequest request) {
        SwitchWechatIdPair pair = SwitchWechatIdPair.builder()
                .requesterUserId(request.getRequesterUserId())
                .receiverUserId(request.getReceiverUserId())
                .approvingStatus(APPROVED)
                .approveTimestamp(System.currentTimeMillis())
                .build();

        mapper.approveSwitchWechatIdRequest(pair);

        return mapper.getSwitchInfoBy2Ids(pair);
    }
}
