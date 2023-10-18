package com.tencent.wxcloudrun.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String openId;
    private String profileImageUrl;
    private String userName;
    private String location;
    private String locationId;
    private String gender;
    private String wechatId;
}
