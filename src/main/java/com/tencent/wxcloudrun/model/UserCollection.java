package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCollection {
    private String userId;
    private String articleId;
    private String articleAuthorName;
    private String articleTitle;
    private String articlePicUrl;
}
