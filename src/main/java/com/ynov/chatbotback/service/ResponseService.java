package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.Message;
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

        return new WebhookResponse()
                .setFulfillmentText(messages.stream().findFirst().orElse(""))
                .setFulfillmentMessages(
                        List.of(new Message()
                                .setText(new Text()
                                        .setText(messages)))
                );
    }
}
