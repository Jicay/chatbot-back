package com.ynov.chatbotback.controller;

import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.service.WebhookInvocator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
public class Controller {

    private WebhookInvocator webhookInvocator;

    @GetMapping("/hello")
    public String get() {
        return "Hello world !";
    }

    @PostMapping("/webhook")
    public WebhookResponse matchIntent(@RequestBody WebhookRequest request) {
        log.info("{}", request);
        var webhookResponse = webhookInvocator.execute(request);
        log.info("{}", webhookResponse);
        return webhookResponse;
    }

}
