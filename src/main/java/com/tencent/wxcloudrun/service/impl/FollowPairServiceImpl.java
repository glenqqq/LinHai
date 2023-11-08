package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.FollowMapper;
import com.tencent.wxcloudrun.dto.followPair.CreateFollowPairRequest;
import com.tencent.wxcloudrun.model.FollowPair;
import com.tencent.wxcloudrun.service.FollowPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowPairServiceImpl implements FollowPairService {

    FollowMapper mapper;

    public FollowPairServiceImpl(@Autowired FollowMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String createFollowPair(CreateFollowPairRequest createFollowPairRequest) {
        final String followId = UUID.randomUUID().toString();
        FollowPair pair = FollowPair.builder()
                .followId(followId)
                .followedUserId(createFollowPairRequest.getFollowedUserId())
                .followedUserName(createFollowPairRequest.getFollowedUserName())
                .followedUserProfileUrl(createFollowPairRequest.getFollowedUserProfileUrl())
                .followingUserId(createFollowPairRequest.getFollowingUserId())
                .followingUserName(createFollowPairRequest.getFollowingUserName())
                .followingUserProfileUrl(createFollowPairRequest.getFollowingUserProfileUrl())
                .build();
        mapper.createFollowPair(pair);
        return followId;
    }

    @Override
    public List<FollowPair> getMyFollowingUser(String userId) {
        return mapper.getMyFollowingUser(userId);
    }

    @Override
    public List<FollowPair> getMyFollowedUser(String userId) {
        return mapper.getMyFollowedUser(userId);
    }
}
