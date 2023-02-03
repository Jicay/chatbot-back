package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Genre;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.Payload;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.Suggestion;
import com.ynov.chatbotback.model.response.Suggestions;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.model.response.slack.Attachment;
import com.ynov.chatbotback.model.response.slack.Block;
import com.ynov.chatbotback.model.response.slack.Element;
import com.ynov.chatbotback.model.response.slack.SlackPayload;
import com.ynov.chatbotback.model.response.slack.TextButton;
import com.ynov.chatbotback.repository.MessageRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AskGenreCommand implements WebhookCommand {


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
                                                        new SimpleResponse().setTextToSpeech(messages.stream().findFirst().orElse(""))))),
                                new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setSuggestions(new Suggestions()
                                                .setSuggestions(
                                                        Arrays.stream(Genre.values())
                                                                .map(it -> new Suggestion().setTitle(it.getLabel()))
                                                                .collect(Collectors.toList())
                                                )),
                                new Message()
                                        .setPlatform(Platform.SLACK)
                                        .setPayload(new Payload()
                                                .setSlack(new SlackPayload()
                                                        .setText(messages.stream().findFirst().orElse(""))
                                                        .setAttachments(List.of(
                                                                new Attachment()
                                                                        .setBlocks(List.of(
                                                                                new Block()
                                                                                        .setType(Block.ACTIONS)
                                                                                        .setElements(
                                                                                                Arrays.stream(Genre.values())
                                                                                                        .map(it -> new Element()
                                                                                                                .setText(new TextButton()
                                                                                                                        .setText(
                                                                                                                                it.getLabel()))
                                                                                                                .setValue(it.getLabel()))
                                                                                                        .collect(Collectors.toList())
                                                                                        ))
                                                                        ))
                                                        )))
                )
                );
    }
}
