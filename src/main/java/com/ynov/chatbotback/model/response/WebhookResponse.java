package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class WebhookResponse {
    private List<Message> fulfillmentMessages;

    private String fulfillmentText;
}
