package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.user.CreateUserRequest;
import com.tencent.wxcloudrun.dto.user.UpdateUserRequest;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    String createUser(CreateUserRequest request);

    User getUserByUserId(String userId);

    User getUserByOpenId(String openId);

    List<User> searchUserByUserName(String target);

    void updateUserInformation(UpdateUserRequest request);
}
