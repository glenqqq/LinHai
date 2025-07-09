package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    void createUser(User user);

    User getUserByUserId(String userId);


    User getUserByOpenId(String openId);

    User getBasicUserInfo(String userId);
    List<String> getAllUserId();

    List<User> searchUserByUserName(@Param("target") String target);

    void updateUserInformation(User user);


}
