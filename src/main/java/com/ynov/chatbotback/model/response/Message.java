package com.ynov.chatbotback.model.response;

import lombok.Data;

@Data
public class Message {

    private Platform platform;

    private Text text;
    private QuickReplies quickReplies;
}
