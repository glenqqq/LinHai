package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.controller.UserController;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j
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
        logger.info("creating new user: {}", newUser);
        mapper.createUser(newUser);
        return userId;
    }

    @Override
    public User getUserByUserId(String userId) {

        User retrievedUser =  mapper.getUserByUserId(userId);
        logger.info("retrieved user: {}", retrievedUser);
        return retrievedUser;
    }

    @Override
    public List<User> searchUserByUserName(String target) {
        return mapper.searchUserByUserName(target);
    }
}
