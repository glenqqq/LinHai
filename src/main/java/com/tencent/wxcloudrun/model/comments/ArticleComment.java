package com.tencent.wxcloudrun.model.comments;


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
    protected String commentId;
    protected String content;
    protected String authorId;
    protected String authorName;
    protected String repliedCommentId;
    protected String authorProfileUrl;
    protected String articleId;
    protected Long createdTimestamp;
    protected String repliedUserId;
    protected String repliedUserName;
    private List<SubComments> subComments;
}
