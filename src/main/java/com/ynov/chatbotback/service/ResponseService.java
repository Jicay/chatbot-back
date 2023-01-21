package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.QuickReplies;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.Suggestion;
import com.ynov.chatbotback.model.response.Suggestions;
import com.ynov.chatbotback.model.response.Text;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.repository.MessageRepository;
import com.ynov.chatbotback.repository.Step;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class ResponseService {

    private MessageRepository messageRepository;

    public WebhookResponse computeResponse(WebhookRequest request) {
        if (request.getQueryResult().getAction().equals("movie.advice")) {
            return computeAdvice(request);
        }
        return null;
    }

    private WebhookResponse computeAdvice(WebhookRequest request) {

        String genre = (String) request.getQueryResult().getParameters().get("genre");
        Step step = Step.AFFICHAGE_RESULTAT;

        if (!StringUtils.hasText(genre)) {
            step = Step.GENRE_QUESTION;
        }
        List<String> messages = messageRepository.findAllByActionAndStep(request.getQueryResult().getAction(), step);

        if (step == Step.GENRE_QUESTION) {
            return new WebhookResponse()
                    //.setFulfillmentText(messages.stream().findFirst().orElse(""))
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
                                                    .setSuggestions(List.of(
                                                            new Suggestion().setTitle("Science-fiction"),
                                                            new Suggestion().setTitle("Comédie"),
                                                            new Suggestion().setTitle("Aventure"),
                                                            new Suggestion().setTitle("Action"))))));
        } else {
            return new WebhookResponse()
                    .setFulfillmentText(messages.stream().findFirst().orElse(""))
                    .setFulfillmentMessages(
                            List.of(new Message()
                                    .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                    .setText(new Text()
                                            .setText(messages)))
                    );
        }
    }
}
