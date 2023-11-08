package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.userCollection.CreateUserCollectionRequest;
import com.tencent.wxcloudrun.model.UserCollection;

import java.util.List;

public interface UserCollectionService {
    void createUserCollection(CreateUserCollectionRequest createUserCollectionRequest);
    List<UserCollection> getMyArticleCollections(String userId);
}
