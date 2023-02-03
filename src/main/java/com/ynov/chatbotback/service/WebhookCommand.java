package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.WebhookResponse;

public interface WebhookCommand {
    WebhookResponse execute(WebhookRequest webhookRequest, Step step);
}
