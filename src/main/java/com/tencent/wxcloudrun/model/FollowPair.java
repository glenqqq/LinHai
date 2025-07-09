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

    @Override
    public String toString() {
        return "FollowPair{" +
                "followId='" + followId + '\'' +
                ", followedUserId='" + followedUserId + '\'' +
                ", followedUserName='" + followedUserName + '\'' +
                ", followedUserProfileUrl='" + followedUserProfileUrl + '\'' +
                ", followingUserId='" + followingUserId + '\'' +
                ", followingUserName='" + followingUserName + '\'' +
                ", followingUserProfileUrl='" + followingUserProfileUrl + '\'' +
                '}';
    }
}
