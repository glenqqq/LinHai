package com.tencent.wxcloudrun.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    //二级评论为被折叠的评论
    //三级评论为回复二级评论&三级评论的评论

    private String content;
    private String authorId;
    private String repliedUserId;//二级评论不需要，三级评论需要
    private String repliedCommentId;//二级评论和三级评论需要
    private String articleId;
    private String articleAuthorId;
}
