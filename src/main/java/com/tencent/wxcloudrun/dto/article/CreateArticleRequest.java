package com.tencent.wxcloudrun.dto.article;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateArticleRequest {
    private String title;
    private Integer articleType;
    private String merchantCategory;
    private List<String> images;
    private String content; // default empty
    private Double price;
    private Boolean isDeliveryCovered;
    private Integer deliveryMethod;
    private String location;
    private Boolean isNeedHelp;
    private Long createTimestamp;
    private Long updateTimestamp;
    private String authorName;
    private String authorId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long likeCount;
    private Long viewCount;
    private Long colCount;
    private Boolean isStickTop;
}
