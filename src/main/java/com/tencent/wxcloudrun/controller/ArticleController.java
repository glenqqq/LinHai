package com.tencent.wxcloudrun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.ArticleMapper;
import com.tencent.wxcloudrun.dto.article.CreateArticleRequest;
import com.tencent.wxcloudrun.dto.article.IsHiddenRequest;
import com.tencent.wxcloudrun.dto.article.TopTitle;
import com.tencent.wxcloudrun.model.Article;
import com.tencent.wxcloudrun.model.FollowPair;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserCollection;
import com.tencent.wxcloudrun.service.ArticleSerice;
import com.tencent.wxcloudrun.service.FollowPairService;
import com.tencent.wxcloudrun.service.UserCollectionService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.Haversine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class ArticleController {
    final ArticleSerice articleSerice;
    final FollowPairService followPairService;
    final UserService userService;
    final ArticleMapper articleMapper;
    final Logger logger;
    final private UserCollectionService userCollectionService;
    public ArticleController(@Autowired ArticleSerice articleSerice, @Autowired FollowPairService followPairService, UserService userService, ArticleMapper articleMapper, UserCollectionService userCollectionService) {
        this.articleSerice = articleSerice;
        this.followPairService = followPairService;
        this.userService = userService;
        this.articleMapper = articleMapper;
        this.userCollectionService = userCollectionService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    /**
     * 创建新帖子
     * @return API response json
     */
    @PostMapping(value = "/api/article-management")
    ApiResponse createArticle(@RequestBody CreateArticleRequest request) {
        logger.info("/api/article createArticle request: {}", request);
        final String articleId = articleSerice.createArticle(request);

        return ApiResponse.ok(articleId);
    }

    /**
     * 增加帖子浏览量
     * @return
     */
    @GetMapping(value = "/api/article-management-addview/{articleId}")
    ApiResponse updateArticleAddView(@PathVariable String articleId) {
        Article articleById = articleMapper.getArticleById(articleId);
        System.out.println("帖子内容："+articleById);
        articleById.setViewCount(articleById.getViewCount()==null?0+1: articleById.getViewCount()+ 1);
        articleMapper.updateArticleViewById(articleById);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/article-management/{articleId}")
    ApiResponse getArticleById(@PathVariable String articleId) {
        logger.info(String.format("requesting article by id: %s", articleId));
        Article retrievedArticle = articleSerice.getArticleById(articleId);
        return ApiResponse.ok(retrievedArticle);
    }

    @GetMapping(value = "/api/article-management/search-by-tile/{target}")
    ApiResponse searchArticleByTitle(@PathVariable String target) {
        logger.info(String.format("requesting search article by title: %s", target));
        List<Article> retrievedArticle = articleSerice.searchArticleByTitle(target);
        for (Article article : retrievedArticle) {
            if (!StringUtils.isEmpty(article.getAuthorId())) {
                User userByUserId = userService.getUserByUserId(article.getAuthorId());
                article.setAuthorProfileUrl(userByUserId.getProfileImageUrl());
                article.setAuthorName(userByUserId.getUserName());
            }
        }

        return ApiResponse.ok(retrievedArticle);
    }

    @GetMapping(value = "/api/article-management/recent-ten-articles")
    ApiResponse getTenArticles(@RequestParam int pageNo, @RequestParam int pageSize
            , @RequestParam String articleType
            , @RequestParam String userId
            , @RequestParam(required = false, name = "newSearchType") Integer newSearchType
            , @RequestParam(required = false, name = "latitudemy") String latitudemy
            , @RequestParam(required = false, name = "longitudemy") String longitudemy
            , @RequestParam(required = false, name = "title") String title
    ) {


        logger.info(String.format("get recent 10 articles　："));
        logger.info(userId);

        System.out.println("前端传递的值newSearchType:"+newSearchType);
        System.out.println("前端传递的值latitudemy:"+latitudemy);
        System.out.println("前端传递的值longitudemy:"+longitudemy);

        logger.info(String.format("查询：：：：：：：：：："));
        Integer articleTypearticleType = Integer.parseInt(articleType);
        List<Article> retrievedArticle = articleSerice.getRecentTenArticles(1, 999999999, articleTypearticleType, newSearchType,title);
        logger.info(String.format("查询------------"));
        if (articleTypearticleType == 0) {
            List<Article> articlesLi = articleSerice.getRecentTenArticles(1, 999999999, articleTypearticleType, newSearchType,title);

            for (Article article : articlesLi) {
                //todo 计算距离
                if (!StringUtils.isEmpty(latitudemy) && !StringUtils.isEmpty(longitudemy) && !StringUtils.isEmpty(article.getLatitude()) && !StringUtils.isEmpty(article.getLongitude())) {
                    BigDecimal bigDecimal = Haversine.calculateDistance(new BigDecimal(latitudemy), new BigDecimal(longitudemy), article.getLatitude(), article.getLongitude());
                    BigDecimal distance = bigDecimal.setScale(2, RoundingMode.HALF_UP);//两地相距
                    article.setDistance(distance);
                }
            }

            if (!StringUtils.isEmpty(newSearchType) && newSearchType == 3) {
                System.out.println("距离排序1");

                //todo 根据距离排序
                articlesLi.sort(new Comparator<Article>() {
                    @Override
                    public int compare(Article a1, Article a2) {
                        // 如果 distance 为 null，则视为最大，放到后面
                        if (a1.getDistance() == null && a2.getDistance() == null) {
                            return 0;
                        } else if (a1.getDistance() == null) {
                            return 1;
                        } else if (a2.getDistance() == null) {
                            return -1;
                        } else {
                            // 如果都不为 null，按 distance 从小到大排序
                            return a1.getDistance().compareTo(a2.getDistance());
                        }
                    }
                });
            }



            List<FollowPair> myFollowedUser = followPairService.getMyFollowingUser(userId);

            // 计算起始索引
            int startIndex = (pageNo - 1) * pageSize;

            List<Article> matchedArticles = articlesLi.stream()
                    .filter(article -> myFollowedUser.stream()
                            .anyMatch(pair -> pair.getFollowedUserId().equals(article.getAuthorId())))
                    .collect(Collectors.toList());

            // 计算结束索引，不超过 matchedArticles 的大小
            int endIndex = Math.min(startIndex + pageSize, matchedArticles.size());
            List<Article> articles = matchedArticles.subList(startIndex, endIndex);
            for (Article article : articles) {
                if (!StringUtils.isEmpty(article.getAuthorId())) {
                    User userByUserId = userService.getUserByUserId(article.getAuthorId());
                    article.setAuthorProfileUrl(userByUserId.getProfileImageUrl());
                    article.setAuthorName(userByUserId.getUserName());
                }
            }

            return ApiResponse.ok(articles);
        }

        for (Article article : retrievedArticle) {
            if (!StringUtils.isEmpty(article.getAuthorId())) {
                User userByUserId = userService.getUserByUserId(article.getAuthorId());
                article.setAuthorProfileUrl(userByUserId.getProfileImageUrl());
                article.setAuthorName(userByUserId.getUserName());
            }
            //todo 计算距离
            if (!StringUtils.isEmpty(latitudemy) && !StringUtils.isEmpty(longitudemy) && !StringUtils.isEmpty(article.getLatitude()) && !StringUtils.isEmpty(article.getLongitude())) {
                BigDecimal bigDecimal = Haversine.calculateDistance(new BigDecimal(latitudemy), new BigDecimal(longitudemy), article.getLatitude(), article.getLongitude());
                BigDecimal distance = bigDecimal.setScale(2, RoundingMode.HALF_UP);//两地相距
                article.setDistance(distance);
            }
        }

        if (!StringUtils.isEmpty(newSearchType) && newSearchType == 3){
            System.out.println("距离排序2");

            //todo 根据距离排序
            retrievedArticle.sort(new Comparator<Article>() {
                @Override
                public int compare(Article a1, Article a2) {
                    // 如果 distance 为 null，则视为最大，放到后面
                    if (a1.getDistance() == null && a2.getDistance() == null) {
                        return 0;
                    } else if (a1.getDistance() == null) {
                        return 1;
                    } else if (a2.getDistance() == null) {
                        return -1;
                    } else {
                        // 如果都不为 null，按 distance 从小到大排序
                        return a1.getDistance().compareTo(a2.getDistance());
                    }
                }
            });
        }

        // todo 分页
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, retrievedArticle.size());

        return ApiResponse.ok(retrievedArticle.subList(startIndex, endIndex));
    }

    @GetMapping(value = "/api/article-management/by-user-id/{userId}")
    ApiResponse getArticleByUserId(@PathVariable String userId) {
        logger.info(String.format("get articles for user id %s", userId));
        List<Article> retrievedArticle = articleSerice.getArticleByUserId(userId);
        return ApiResponse.ok(retrievedArticle);
    }

    @PostMapping(value = "/api/article-management/hide-article")
    ApiResponse hideArticle(@RequestBody IsHiddenRequest request) {
        logger.info(String.format("hide article: %s", request.getArticleId()));
        articleSerice.hideArticle(request.getArticleId());
        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/article-management/show-article")
    ApiResponse showArticle(@RequestBody IsHiddenRequest request) {
        logger.info(String.format("show article: %s", request.getArticleId()));
        articleSerice.showArticle(request.getArticleId());
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/article-management/num-by-user-id/{userId}")
    ApiResponse getArticleNumByUserId(@PathVariable String userId) {
        logger.info(String.format("get articles number for user id %s", userId));
        return ApiResponse.ok(articleSerice.getArticleNumByUserId(userId));
    }
}
