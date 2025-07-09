package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.ChatRecode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatHistoryMapper {
   List<ChatRecode> searchChatByUserId(ChatRecode chatHistory);
   List<ChatRecode> searchChatListByUserId(ChatRecode chatHistory);
   List<ChatRecode> searchMyChatByUserId(String userid);
   void updateChat(ChatRecode chatHistory);
   void updateChatread(ChatRecode chatHistory);
   void insertChat(ChatRecode chatHistory);
}
