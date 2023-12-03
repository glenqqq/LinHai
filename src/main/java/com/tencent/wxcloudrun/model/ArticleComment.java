package com.tencent.wxcloudrun.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
