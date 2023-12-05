package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMessageMapper {
    List<UserMessage> getMyUserMessage(String userId);
    void createUserMessage(UserMessage userMessage);
}
