package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMessageMapper {
    List<UserMessage> getAllMyWechatMessage(String userId);
    List<UserMessage> getMyUserMessage(String userId);
    List<UserMessage> getUserMessageByRequestUserIdAndReceviceUserId(String requestingUserId,String receiverUserId);
    UserMessage readMessageById(String messageId);
    void createUserMessage(UserMessage userMessage);
    void readMessage(String messageId);
    void unreadMessage(String messageId);
}
