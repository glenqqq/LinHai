package com.tencent.wxcloudrun.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    //二级评论为被折叠的评论
    //三级评论为回复二级评论&三级评论的评论

    private String content;
    private String authorId;

    private String articleId;
    private String articleAuthorId;

    private String rootCommentId; // 一级评论id, 二级三级评论都需要, 一级评论不需要
    private String rootCommentAuthorId; // 一级评论作者id, 二级三级评论都需要, 一级评论不需要
    private String leaveCommentId; // 二级评论id, 三级评论需要， 一级 二级评论不需要
    private String leaveCommentAuthorId; //二级评论作者id, 三级评论需要， 一级 二级评论不需要
}
