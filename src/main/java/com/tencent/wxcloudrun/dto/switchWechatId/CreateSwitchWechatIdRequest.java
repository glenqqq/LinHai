package com.tencent.wxcloudrun.dto.switchWechatId;

import lombok.Data;

@Data
public class CreateSwitchWechatIdRequest {
    private String requesterUserId;
    private String requesterUserName;
    private String requesterUserProfileUrl;
    private String requesterUserWechatId;
    private String requesterUserLocation;
    private String requesterUserLocationId;

    private String receiverUserId;
    private String receiverUserName;
    private String receiverUserProfileUrl;
    private String receiverUserWechatId;
    private String receiverUserLocation;
    private String receiverUserLocationId;

    private String resourceArticleId;
    private String resourceArticleTitle;
    private String resourceArticleFirstPicUrl;
    private Long creationTimestamp;
    private Long approveTimestamp;
    private Long lastUpdatedTimestamp;
    private String approvingStatus;
}
