package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserCollectionMapper {
    void createUserCollection(UserCollection userCollection);
    List<UserCollection> getMyCollectionList(String userId);

    UserCollection getByUserIdAndArticleId(UserCollection userCollection);
}
