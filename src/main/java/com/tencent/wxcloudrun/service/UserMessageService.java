package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.UserMessage;

import java.util.List;

public interface UserMessageService {
    List<UserMessage> getMyUserMessage(String userId);

    void createSystemMessageForArticle(String article);
}
