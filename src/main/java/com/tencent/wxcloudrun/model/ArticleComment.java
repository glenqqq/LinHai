package com.tencent.wxcloudrun.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ArticleComment {
    private String commentId;
    private String content;
    private String authorId;
    private String authorName;
    private String repliedCommentId;
    private String authorProfileUrl;
    private String articleId;
    private Long createdTimestamp;
    private String repliedUserId;
    private String repliedUserName;
    private List<ArticleComment> subComments;
}
