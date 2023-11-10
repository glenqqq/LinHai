package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SwitchWechatIdPair {

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
