package com.ynov.chatbotback;

import lombok.Data;

@Data
public class WebhookRequest {
    private String session;
    private String responseId;
    private QueryResult queryResult;
}
