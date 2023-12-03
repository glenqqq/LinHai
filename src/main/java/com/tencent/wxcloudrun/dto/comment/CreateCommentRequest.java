package com.tencent.wxcloudrun.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String content;
    private String authorId;
    private String repliedUserId;
    private String repliedCommentId;
    private String articleId;
}
