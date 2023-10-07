package com.tencent.wxcloudrun.model;

import lombok.Getter;
import lombok.Builder;

@Builder
@Getter
public class User {
    private String openId;
    private String userId;
    private String userName;
    private String profileImageUrl;
    private String location;
    private String locationId;
    private String gender;
    private String longitude;
    private String latitude;
}
