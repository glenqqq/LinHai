package com.tencent.wxcloudrun.model.comments;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SubComments extends ArticleComment{
    private String rootCommentAuthorName;
    public SubComments(ArticleComment articleComment) {
        this.commentId = articleComment.getCommentId();
        this.content = articleComment.getContent();
        this.authorId = articleComment.getAuthorId();
        this.authorName = articleComment.getAuthorName();
        this.authorProfileUrl = articleComment.getAuthorProfileUrl();
        this.articleId = articleComment.getArticleId();
        this.articleAuthorId = articleComment.getArticleAuthorId();
        this.createdTimestamp = articleComment.getCreatedTimestamp();
        this.rootCommentId = articleComment.getRootCommentId();
        this.rootCommentAuthorId = articleComment.getRootCommentAuthorId();
        this.leaveCommentId = articleComment.getLeaveCommentId();
        this.leaveCommentAuthorId = articleComment.getLeaveCommentAuthorId();
        this.leaveCommentAuthorName = articleComment.getLeaveCommentAuthorName();
    }
}
