package com.tencent.wxcloudrun.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserId(session);
        if (userId != null) {
            userSessions.put(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Parse incoming message
        String payload = message.getPayload();
        System.out.println("接收到的 message payload: " + payload); // 打印接收到的消息

        // Assuming message format includes recipient userId
        String recipientUserId = getRecipientUserId(payload); // Implement this method
        WebSocketSession recipientSession = userSessions.get(recipientUserId);//管理员的userId=1
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(message);
        }
    }

    public boolean isUserOnline(String userId) {
        WebSocketSession session = userSessions.get(userId);
        return session != null && session.isOpen();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserId(session); // Implement this method
        if (userId != null) {
            userSessions.remove(userId);
        }
    }

    private String getRecipientUserId(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.readTree(payload);
        System.out.println("消息："+jsonObject.get("sendedUserId").asText());
        return jsonObject.get("sendedUserId").asText();//要发送的对方的userid
    }

    private String extractUserId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String query = uri.contains("?") ? uri.substring(uri.indexOf("?") + 1) : ""; // 获取问号后的查询字符串
        String userIdPrefix = "userId=";

        // 查找 userId= 的起始位置
        int startIndex = query.indexOf(userIdPrefix);
        if (startIndex != -1) {
            // 获取 userId 后面的字符串
            int endIndex = query.indexOf("&", startIndex); // 查找下一个参数的起始位置
            if (endIndex != -1) {
                // 如果存在其他参数，截取 userId 的值
                return query.substring(startIndex + userIdPrefix.length(), endIndex);
            } else {
                // 如果 userId 是最后一个参数，则取到字符串末尾
                return query.substring(startIndex + userIdPrefix.length());
            }
        }

        return null; // 如果没有找到 userId 参数
    }
}
