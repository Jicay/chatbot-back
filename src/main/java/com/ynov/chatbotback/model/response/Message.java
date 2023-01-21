package com.ynov.chatbotback.model.response;

import lombok.Data;

@Data
public class Message {

    private Text text;
    private QuickReplies quickReplies;
}
