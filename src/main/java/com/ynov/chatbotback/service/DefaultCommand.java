package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.repository.MessageRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultCommand implements WebhookCommand {


    private MessageRepository messageRepository;

    @Override
    public WebhookResponse execute(WebhookRequest request, Step step) {
        List<String> messages = messageRepository.findAllByActionAndStep(step);

        return new WebhookResponse()
                .setFulfillmentMessages(
                        List.of(
                                new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setSimpleResponses(new SimpleResponses()
                                                .setSimpleResponses(List.of(
                                                        new SimpleResponse().setTextToSpeech(messages.stream().findFirst().orElse("")))))));
    }
}
