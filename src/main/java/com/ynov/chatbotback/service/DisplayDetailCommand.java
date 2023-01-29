package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Movie;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.BasicCard;
import com.ynov.chatbotback.model.response.Button;
import com.ynov.chatbotback.model.response.Image;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.OpenUriAction;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.Text;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.repository.MessageRepository;
import com.ynov.chatbotback.repository.MovieRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DisplayDetailCommand implements WebhookCommand {


    private MessageRepository messageRepository;

    private MovieRepository movieRepository;

    @Override
    public WebhookResponse execute(WebhookRequest request) {
        List<String> messages = messageRepository.findAllByActionAndStep(Step.DISPLAY_DETAIL);

        Optional<String> chosenOption = request.getQueryResult().getOutputContexts().stream()
                .filter(it -> it.getName().endsWith("actions_intent_option"))
                .map(it -> it.getParameters().get("OPTION"))
                .findFirst();
        if (chosenOption.isEmpty()) {
            return new WebhookResponse()
                    .setFulfillmentText("Je suis désolé, je ne retrouve pas le film")
                    .setFulfillmentMessages(
                            List.of(new Message()
                                    .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                    .setText(new Text()
                                            .setText(List.of("Je suis désolé, je ne retrouve pas le film"))))
                    );
        }

        Movie movie = movieRepository.findDetailById(chosenOption.get());

        return new WebhookResponse()
                .setFulfillmentText(messages.stream().findFirst().orElse(""))
                .setFulfillmentMessages(
                        List.of(
                                new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setSimpleResponses(new SimpleResponses()
                                                .setSimpleResponses(List.of(
                                                        new SimpleResponse().setTextToSpeech(messages.stream().findFirst().orElse(""))))),

                                new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setBasicCard(new BasicCard()
                                                .setTitle(movie.getTitle())
                                                .setFormattedText(movie.getOverview())
                                                .setSubtitle("Sorti le " + movie.getReleaseDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRANCE)))
                                                .setImage(new Image().setImageUri(movie.getImageUrl()))
                                                .setButtons(List.of(new Button()
                                                        .setTitle("Voir sur The Movie DB")
                                                        .setOpenUriAction(new OpenUriAction().setUri("https://www.themoviedb.org/movie/" + movie.getId())))))
                        )
                );
    }
}
