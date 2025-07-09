package com.tencent.wxcloudrun.model.comments;

import lombok.Data;

@Data
public class CommentList {
    protected String commentId;

    protected String content;//内容

    protected String authorId;//评论人
    protected String authorName;
    protected String authorProfileUrl;
    protected Long createdTimestamp;

    protected String articleId;//帖子
    protected String articleTitle;
    protected String articleImg;

    protected String rootCommentId; // 一级评论id, 二级三级评论都需要
    protected String rootCommentAuthorId; // 一级评论作者id, 二级三级评论都需要
    protected String rootAuthorName;
    protected String rootAuthorProfileUrl;
    protected String rootCommentContent;
}
