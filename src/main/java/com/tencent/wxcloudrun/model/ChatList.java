package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class ChatList {
    private String otherUserId;
    private String avatar;
    private String nickname;
    private String message;
    private Integer unreadCount;
    private String time;
}
