package com.tencent.wxcloudrun.dto.switchWechatId;

import lombok.Data;

@Data
public class ApproveSwitchWechatIdRequest {
    private String requesterUserId;
    private String receiverUserId;
}
