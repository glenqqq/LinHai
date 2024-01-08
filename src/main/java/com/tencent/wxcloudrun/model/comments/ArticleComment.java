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
    protected String authorProfileUrl;
    protected String articleId;
    protected String articleAuthorId;
    protected Long createdTimestamp;
    protected String rootCommentId; // 一级评论id, 二级三级评论都需要
    protected String rootCommentAuthorId; // 一级评论作者id, 二级三级评论都需要
    protected String leaveCommentId; // 二级评论id, 三级评论需要， 二级评论不需要
    protected String leaveCommentAuthorId; //二级评论作者id, 三级评论需要， 二级评论不需要
    protected String leaveCommentAuthorName; //二级评论作者id, 三级评论需要， 二级评论不需要
    private List<SubComments> subComments;
}
