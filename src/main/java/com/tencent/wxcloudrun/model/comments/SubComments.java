package com.tencent.wxcloudrun.model.comments;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class SubComments extends ArticleComment{
    public SubComments(ArticleComment articleComment) {
        this.commentId = articleComment.getCommentId();
        this.content = articleComment.getContent();
        this.authorName = articleComment.getAuthorName();
        this.articleId = articleComment.getArticleId();
        this.repliedCommentId = articleComment.getRepliedCommentId();
        this.authorProfileUrl = articleComment.getAuthorProfileUrl();
        this.createdTimestamp = articleComment.getCreatedTimestamp();
        this.repliedUserId = articleComment.getRepliedUserId();
        this.authorId = articleComment.getAuthorId();
        this.repliedUserName = articleComment.getRepliedUserName();
    }
}
