package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.FollowPair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FollowMapper {
    void createFollowPair(FollowPair followPair);
    List<FollowPair> getMyFollowingUser(String userId);
    List<FollowPair> getMyFollowedUser(String userId);
}
