package com.ynov.chatbotback.model.request;

import lombok.Data;

@Data
public class WebhookRequest {
    private String session;
    private String responseId;
    private QueryResult queryResult;
}
