package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.FollowMapper;
import com.tencent.wxcloudrun.dao.UserCollectionMapper;
import com.tencent.wxcloudrun.dto.userCollection.CreateUserCollectionRequest;
import com.tencent.wxcloudrun.model.UserCollection;
import com.tencent.wxcloudrun.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectionServiceImpl implements UserCollectionService {
    final UserCollectionMapper mapper;

    public UserCollectionServiceImpl(@Autowired UserCollectionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void createUserCollection(CreateUserCollectionRequest createUserCollectionRequest) {
        UserCollection userCollection = UserCollection.builder()
                .userId(createUserCollectionRequest.getUserId())
                .articleId(createUserCollectionRequest.getArticleId())
                .articleAuthorName(createUserCollectionRequest.getArticleAuthorName())
                .articleTitle(createUserCollectionRequest.getArticleTitle())
                .articlePicUrl(createUserCollectionRequest.getArticlePicUrl())
                .build();
        mapper.createUserCollection(userCollection);
    }

    @Override
    public List<UserCollection> getMyArticleCollections(String userId) {
        return mapper.getMyCollectionList(userId);
    }
}
