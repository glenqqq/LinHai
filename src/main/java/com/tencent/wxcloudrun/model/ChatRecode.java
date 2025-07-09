package com.tencent.wxcloudrun.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 聊天记录JSON格式
 * @date 2025/01/06
 */
@Data
public class ChatRecode {
    private String id;
    private String sendedUserId;
    private String userid;
    private String contents;//内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    private Boolean isread;
}
