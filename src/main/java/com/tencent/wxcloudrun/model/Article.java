package com.tencent.wxcloudrun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String articleId;
    private String title;
    private Integer articleType;
    private String merchantCategory;
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
    private String authorProfileUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal distance;//相距

    private Long likeCount;
    private Long viewCount;
    private Long colCount;
    private Boolean isStickTop;
    private String images;
    private Boolean isHidden;

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", articleType=" + articleType +
                ", merchantCategory='" + merchantCategory + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", isDeliveryCovered=" + isDeliveryCovered +
                ", deliveryMethod=" + deliveryMethod +
                ", location='" + location + '\'' +
                ", isNeedHelp=" + isNeedHelp +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", authorName='" + authorName + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorProfileUrl='" + authorProfileUrl + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", likeCount=" + likeCount +
                ", viewCount=" + viewCount +
                ", colCount=" + colCount +
                ", isStickTop=" + isStickTop +
                ", images='" + images + '\'' +
                ", isHidden=" + isHidden +
                '}';
    }
}
