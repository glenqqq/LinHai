package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.ChatWebSocketHandler;
import com.tencent.wxcloudrun.dao.ChatHistoryMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.ChatList;
import com.tencent.wxcloudrun.model.ChatRecode;
import com.tencent.wxcloudrun.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class ChatHistoryController {
    final ChatHistoryMapper chatHistoryMapper;
    final UserMapper userMapper;
    private final ChatWebSocketHandler chatWebSocketHandler;
    final Logger logger;

    public ChatHistoryController(ChatHistoryMapper chatHistoryMapper, UserMapper userMapper, ChatWebSocketHandler chatWebSocketHandler) {
        this.chatHistoryMapper = chatHistoryMapper;
        this.userMapper = userMapper;
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @PostMapping(value = "/api/chat-create")
    ApiResponse createChatHistory(@RequestBody ChatRecode chatHistory) {
        logger.info("/api/chat-management createChatHistory 新建会话 request: {}", chatHistory);
        chatHistoryMapper.insertChat(chatHistory);
        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/chat-update")
    ApiResponse updateChatHistory(@RequestBody ChatRecode chatHistory) {
        logger.info("/api/chat-management createChatHistory 修改记录 request: {}", chatHistory);
        chatHistoryMapper.updateChat(chatHistory);
        return ApiResponse.ok();
    }

    @PostMapping(value = "/api/chat-read")
    ApiResponse updateChatread(@RequestBody ChatRecode chatHistory) {
        logger.info("/api/chat-management createChatHistory 已读 request: {}", chatHistory);
        chatHistoryMapper.updateChatread(chatHistory);
        return ApiResponse.ok();
    }


    @GetMapping(value = "/api/chat-management")
    ApiResponse getArticleById(ChatRecode chatHistory) {
        logger.info(String.format("查询两个人的聊天记录 : %s", chatHistory));
        List<ChatRecode> chatHistoryList = chatHistoryMapper.searchChatByUserId(chatHistory);
        return ApiResponse.ok(chatHistoryList);
    }

    @GetMapping(value = "/api/mychat-management")
    ApiResponse getMyChatById(ChatRecode chatHistory) {
        logger.info(String.format("查询自己的聊天记录 : %s", chatHistory));
        List<ChatList> chatListList = new ArrayList<>();

        List<ChatRecode> chatHistoryList = chatHistoryMapper.searchMyChatByUserId(chatHistory.getUserid());
        List<String> stringListMap = groupChatRecords(chatHistory.getUserid(), chatHistoryList);

        for (String otherUserid : stringListMap) {
            ChatRecode chatRecode = new ChatRecode();
            chatRecode.setUserid(otherUserid);
            chatRecode.setSendedUserId(chatHistory.getUserid());
            List<ChatRecode> chatRecodes = chatHistoryMapper.searchChatListByUserId(chatRecode);//未读数量

            //查基本信息
            User userByUserId = userMapper.getUserByUserId(otherUserid);

            ChatList chatList = new ChatList();
            chatList.setUnreadCount(chatRecodes==null? 0 : chatRecodes.size());
            chatList.setAvatar(userByUserId.getProfileImageUrl());
            chatList.setNickname(userByUserId.getUserName());
            chatList.setOtherUserId(otherUserid);

            ChatRecode chat = new ChatRecode();
            chat.setSendedUserId(chatHistory.getUserid());
            chat.setUserid(otherUserid);
            List<ChatRecode> chatRecodes1 = chatHistoryMapper.searchChatByUserId(chat);//查所有消息
            if (chatRecodes1 != null) {
                ChatRecode chatRecode1 = chatRecodes1.get(chatRecodes1.size() - 1);
                chatList.setMessage(chatRecode1.getContents());
                chatList.setTime(formatChatTime(chatRecode1.getApplyTime()));
            }


            chatListList.add(chatList);
        }

        return ApiResponse.ok(chatListList);
    }

    @GetMapping("/api/isOnline")
    public ResponseEntity<Boolean> isUserOnline(@RequestParam String userId) {
        boolean online = chatWebSocketHandler.isUserOnline(userId);
        return ResponseEntity.ok(online);
    }

    public static List<String> groupChatRecords(String currentUserId, List<ChatRecode> chatHistoryList) {
        // 存储所有的对话框，键是对方的 userId，值是和对方的聊天记录列表
        List<String> dialogMap = new ArrayList<>();

        for (ChatRecode record : chatHistoryList) {
            String sender = record.getUserid();
            String receiver = record.getSendedUserId();

            // 确定对方的 userId
            String otherUserId = sender.equals(currentUserId) ? receiver : sender;

            if (dialogMap.contains(otherUserId)) {
                continue;
            }
            System.out.println("对方用户"+otherUserId);
            dialogMap.add(otherUserId);
            // 把聊天记录放到对应的对话框中
        }
        return dialogMap;
    }

    public static String formatChatTime(Date applyTime) {
        if (applyTime == null) {
            return "";
        }

        Calendar now = Calendar.getInstance();
        Calendar messageTime = Calendar.getInstance();
        messageTime.setTime(applyTime);

        SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm"); // 今天的格式：时:分
        SimpleDateFormat thisYearFormat = new SimpleDateFormat("MM-dd HH:mm"); // 今年的格式：月-日 时:分
        SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 其他年份格式：年-月-日 时:分

        if (now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)) {
            if (now.get(Calendar.DAY_OF_YEAR) == messageTime.get(Calendar.DAY_OF_YEAR)) {
                return todayFormat.format(applyTime); // 今天
            } else {
                return thisYearFormat.format(applyTime); // 今年但不是今天
            }
        } else {
            return fullFormat.format(applyTime); // 往年
        }
    }
}
