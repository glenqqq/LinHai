package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper mapper;

    public UserServiceImpl(@Autowired UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String createUser(CreateUserRequest request) {
        final String userId = UUID.randomUUID().toString();
        User newUser = User.builder()
                .userId(userId)
                .openId(request.getOpenId())
                .locationId(request.getLocationId())
                .location(request.getLocation())
                .userName(request.getUserName())
                .profileImageUrl(request.getProfileImageUrl())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .gender(request.getGender())
                .build();
        mapper.createUser(newUser);
        return userId;
    }

    @Override
    public User getUserByUserId(String userId) {
        return mapper.getUserByUserId(userId);
    }

    @Override
    public List<User> searchUserByUserName(String target) {
        return mapper.searchUserByUserName(target);
    }
}
