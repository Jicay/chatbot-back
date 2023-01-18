package com.ynov.chatbotback;

import java.util.List;
import lombok.Data;

@Data
public class WebhookResponse {
    private List<Message> fulfillmentMessages;
}
