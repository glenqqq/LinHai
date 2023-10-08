package com.tencent.wxcloudrun.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String openId;
    private String profileImageUrl;
    private String location;
    private String locationId;
    private String gender;
    private String longitude;
    private String latitude;
}
