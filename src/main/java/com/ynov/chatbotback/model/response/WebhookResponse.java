package com.ynov.chatbotback.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class WebhookResponse {
    private List<Message> fulfillmentMessages;

    private String fulfillmentText;
}
