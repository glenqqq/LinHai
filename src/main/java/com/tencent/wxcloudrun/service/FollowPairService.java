package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.followPair.CreateFollowPairRequest;
import com.tencent.wxcloudrun.model.FollowPair;

import java.util.List;

public interface FollowPairService {
    String createFollowPair(CreateFollowPairRequest createFollowPairRequest);
    String deleteFollowPair(CreateFollowPairRequest createFollowPairRequest);
    List<FollowPair> getMyFollowingUser(String userId);
    List<FollowPair> getMyFollowedUser(String userId);
    Integer getMyFollowedUserNum(String userId);
    Boolean checkIfUserFollowed(String followedUserId, String followingUserId);
}
