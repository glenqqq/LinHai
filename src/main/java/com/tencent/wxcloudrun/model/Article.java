package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Article {
    private String articleId;
    private String title;
}
