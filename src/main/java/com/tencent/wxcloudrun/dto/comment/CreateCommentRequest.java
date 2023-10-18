package com.tencent.wxcloudrun.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String commentId;
    private String content;
    private String authorId;
    private String authorName;
    private String repliedCommentId;
    private Long createdTimestamp;
}
