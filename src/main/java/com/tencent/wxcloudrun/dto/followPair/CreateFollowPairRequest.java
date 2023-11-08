package com.tencent.wxcloudrun.dto.followPair;

import lombok.Data;

@Data
public class CreateFollowPairRequest {
    private String followedUserId;
    private String followedUserName;
    private String followedUserProfileUrl;

    private String followingUserId;
    private String followingUserName;
    private String followingUserProfileUrl;
}
