package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Genre;
import com.ynov.chatbotback.model.Movie;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.BasicCard;
import com.ynov.chatbotback.model.response.Button;
import com.ynov.chatbotback.model.response.CarouselSelect;
import com.ynov.chatbotback.model.response.Image;
import com.ynov.chatbotback.model.response.Item;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.OpenUriAction;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SelectItemInfo;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.Suggestion;
import com.ynov.chatbotback.model.response.Suggestions;
import com.ynov.chatbotback.model.response.Text;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.repository.MessageRepository;
import com.ynov.chatbotback.repository.MovieRepository;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class ResponseService {

    private MessageRepository messageRepository;
    private MovieRepository movieRepository;

    public WebhookResponse computeResponse(WebhookRequest request) {
        if (request.getQueryResult().getAction().equals("movie.advice")) {
            return computeAdvice(request);
        }
        if (request.getQueryResult().getAction().equals("movie.advice.details")) {
            return getDetails(request);
        }
        return null;
    }

    private WebhookResponse getDetails(WebhookRequest request) {
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
                .setFulfillmentText("Voici le détail")
                .setFulfillmentMessages(
                        List.of(
                            new Message()
                                    .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                    .setSimpleResponses(new SimpleResponses()
                                    .setSimpleResponses(List.of(
                                        new SimpleResponse().setTextToSpeech("Voici les informations :")))),

                        new Message()
                                .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                .setBasicCard(new BasicCard()
                                        .setTitle(movie.getTitle())
                                        .setFormattedText(movie.getOverview())
                                        .setSubtitle(movie.getReleaseDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRANCE)))
                                        .setImage(new Image().setImageUri(movie.getImageUrl()))
                                        .setButtons(List.of(new Button()
                                                .setTitle("Voir sur The Movie DB")
                                                .setOpenUriAction(new OpenUriAction().setUri("https://www.themoviedb.org/movie/" + movie.getId())))))
                        )
                );
    }

    private WebhookResponse computeAdvice(WebhookRequest request) {

        String genreInput = (String) request.getQueryResult().getParameters().get("genre");
        Step step = Step.AFFICHAGE_RESULTAT;

        if (!StringUtils.hasText(genreInput)) {
            step = Step.GENRE_QUESTION;
        }
        List<String> messages = messageRepository.findAllByActionAndStep(request.getQueryResult().getAction(), step);

        if (step == Step.GENRE_QUESTION) {
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
                                                            ))));
        } else {
            Genre genre = Genre.findByLabel(genreInput);
            if (genre == null) {
                return new WebhookResponse()
                        .setFulfillmentText("Je suis désolé, je ne reconnais pas ce genre")
                        .setFulfillmentMessages(
                                List.of(new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setText(new Text()
                                                .setText(List.of("Je suis désolé, je ne reconnais pas ce genre"))))
                        );
            }
            List<Movie> movies = movieRepository.findAllByGenre(genre);
            return new WebhookResponse()
                    .setFulfillmentText(messages.stream().findFirst().orElse(""))
                    .setFulfillmentMessages(
                            List.of(
                                new Message()
                                    .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                    .setText(new Text()
                                            .setText(messages)),
                                new Message()
                                        .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                        .setCarouselSelect(new CarouselSelect()
                                                .setItems(
                                                        movies.stream()
                                                                .map(it -> new Item()
                                                                        .setDescription(it.getOverview())
                                                                        .setImage(new Image().setImageUri(it.getImageUrl()))
                                                                        .setTitle(it.getTitle())
                                                                        .setInfo(new SelectItemInfo()
                                                                                .setKey(String.valueOf(it.getId()))
                                                                                .setSynonyms(List.of(it.getTitle()))))
                                                                .collect(Collectors.toList())
                                                ))
                            )
                    );
        }
    }
}
