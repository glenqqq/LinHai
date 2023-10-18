package com.tencent.wxcloudrun.model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleComment {
    private String commentId;
    private String content;
    private String authorId;
    private String authorName;
    private String repliedCommentId;
    private String articleId;
    private Long createdTimestamp;
}
