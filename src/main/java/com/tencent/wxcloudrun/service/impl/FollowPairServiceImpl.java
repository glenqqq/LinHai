package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.FollowMapper;
import com.tencent.wxcloudrun.dao.UserMessageMapper;
import com.tencent.wxcloudrun.dto.followPair.CreateFollowPairRequest;
import com.tencent.wxcloudrun.model.FollowPair;
import com.tencent.wxcloudrun.model.UserMessage;
import com.tencent.wxcloudrun.service.FollowPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowPairServiceImpl implements FollowPairService {

    FollowMapper mapper;
    UserMessageMapper userMessageMapper;

    @Autowired
    public FollowPairServiceImpl(FollowMapper mapper, UserMessageMapper userMessageMapper) {
        this.userMessageMapper = userMessageMapper;
        this.mapper = mapper;
    }

    @Override
    public String createFollowPair(CreateFollowPairRequest createFollowPairRequest) {
        if (checkIfUserFollowed(
                createFollowPairRequest.getFollowedUserId(),
                createFollowPairRequest.getFollowingUserId())) {
            throw new IllegalArgumentException("pair already exists");
        }
        final String followId = UUID.randomUUID().toString();
        final String userMessageId = UUID.randomUUID().toString();
        FollowPair pair = FollowPair.builder()
                .followId(followId)
                .followedUserId(createFollowPairRequest.getFollowedUserId())
                .followedUserName(createFollowPairRequest.getFollowedUserName())
                .followedUserProfileUrl(createFollowPairRequest.getFollowedUserProfileUrl())
                .followingUserId(createFollowPairRequest.getFollowingUserId())
                .followingUserName(createFollowPairRequest.getFollowingUserName())
                .followingUserProfileUrl(createFollowPairRequest.getFollowingUserProfileUrl())
                .build();
        UserMessage userMessage = UserMessage.builder()
                .messageId(userMessageId)
                .receiverUserId(createFollowPairRequest.getFollowedUserId())
                .requestingUserId(createFollowPairRequest.getFollowingUserId())
                .isMessageRead(false)
                .createTimestamp(System.currentTimeMillis())
                .messageType("FOLLOW")
                .build();
        mapper.createFollowPair(pair);
        userMessageMapper.createUserMessage(userMessage);
        return followId;
    }

    @Override
    public String deleteFollowPair(CreateFollowPairRequest createFollowPairRequest) {
        FollowPair pair = FollowPair.builder()
                .followedUserId(createFollowPairRequest.getFollowedUserId())
                .followingUserId(createFollowPairRequest.getFollowingUserId())
                .build();
        mapper.deleteFollowPair(pair);
        return null;
    }

    @Override
    public Integer getMyFollowedUserNum(String userId) {
        List<FollowPair> retrievedFollowed = mapper.getMyFollowedUser(userId);
        if (null == retrievedFollowed) {
            return 0;
        } else {
            return retrievedFollowed.size();
        }
    }

    @Override
    public Boolean checkIfUserFollowed(String followedUserId, String followingUserId) {
        FollowPair followPair = mapper.getByUserIds(
                FollowPair.builder()
                        .followedUserId(followedUserId)
                        .followingUserId(followingUserId)
                        .build()
        );
        return null != followPair;
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
