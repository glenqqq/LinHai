package com.tencent.wxcloudrun.dto.userCollection;

import lombok.Data;

@Data
public class CreateUserCollectionRequest {
    private String userId;
    private String articleId;
    private String articleAuthorName;
    private String articleTitle;
    private String articlePicUrl;
}
