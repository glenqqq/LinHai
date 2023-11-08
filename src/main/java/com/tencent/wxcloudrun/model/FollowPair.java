package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowPair {
    private String followId;

    private String followedUserId;
    private String followedUserName;
    private String followedUserProfileUrl;

    private String followingUserId;
    private String followingUserName;
    private String followingUserProfileUrl;

}
