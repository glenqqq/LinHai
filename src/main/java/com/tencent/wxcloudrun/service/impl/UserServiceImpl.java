package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.controller.UserController;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.dto.user.UpdateUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper mapper;

    final Logger logger;
    public UserServiceImpl(@Autowired UserMapper mapper) {
        this.mapper = mapper;
        this.logger = LoggerFactory.getLogger(UserServiceImpl.class);
    }

    @Override
    public String createUser(CreateUserRequest request) {
        final String userId = UUID.randomUUID().toString();
        final String userName = "Linhai-"+ UUID.randomUUID();
        User newUser = User.builder()
                .userId(userId)
                .openId(request.getOpenId())
                .locationId(request.getLocationId())
                .location(request.getLocation())
                .userName(userName)
                .profileImageUrl(request.getProfileImageUrl())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .gender(request.getGender())
                .build();
        logger.info("creating new user: {}", newUser);
        mapper.createUser(newUser);
        return userName;
    }

    @Override
    public User getUserByUserId(String userId) {

        User retrievedUser =  mapper.getUserByUserId(userId);
        logger.info("retrieved user: {}", retrievedUser);
        return retrievedUser;
    }

    @Override
    public User getUserByOpenId(String openId) {
        User retrievedUser =  mapper.getUserByOpenId(openId);
        logger.info("retrieved user: {}", retrievedUser);
        return retrievedUser;
    }

    @Override
    public List<User> searchUserByUserName(String target) {
        return mapper.searchUserByUserName(target);
    }

    @Override
    public void updateUserInformation(UpdateUserRequest request) {
        User updatedUser = User.builder()
                .openId(request.getOpenId())
                .userName(request.getUserName())
                .location(request.getLocation())
                .locationId(request.getLocationId())
                .gender(request.getGender())
                .wechatId(request.getWechatId())
                .build();
        mapper.updateUserInformation(updatedUser);
    }
}
