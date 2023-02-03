package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Movie;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.BasicCard;
import com.ynov.chatbotback.model.response.Button;
import com.ynov.chatbotback.model.response.Image;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.OpenUriAction;
import com.ynov.chatbotback.model.response.Payload;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.Text;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.model.response.slack.Attachment;
import com.ynov.chatbotback.model.response.slack.Block;
import com.ynov.chatbotback.model.response.slack.Element;
import com.ynov.chatbotback.model.response.slack.SlackPayload;
import com.ynov.chatbotback.model.response.slack.TextButton;
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
    public WebhookResponse execute(WebhookRequest request, Step step) {
        List<String> messages = messageRepository.findAllByActionAndStep(step);

        Optional<String> chosenOption = request.getQueryResult().getOutputContexts().stream()
                .filter(it -> it.getName().endsWith("actions_intent_option"))
                .map(it -> it.getParameters().get("OPTION"))
                .findFirst();
        String movieId = request.getQueryResult().getParameters().get("movieId").toString();
        if (chosenOption.isEmpty() && movieId == null) {
            return new WebhookResponse()
                    .setFulfillmentText("Je suis désolé, je ne retrouve pas le film")
                    .setFulfillmentMessages(
                            List.of(new Message()
                                    .setPlatform(Platform.ACTIONS_ON_GOOGLE)
                                    .setText(new Text()
                                            .setText(List.of("Je suis désolé, je ne retrouve pas le film"))))
                    );
        }

        Movie movie = movieRepository.findDetailById(chosenOption.orElse(movieId));

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
                                                        .setOpenUriAction(new OpenUriAction().setUri("https://www.themoviedb.org/movie/" + movie.getId()))))),
                                new Message()
                                        .setPlatform(Platform.SLACK)
                                        .setPayload(new Payload().setSlack(new SlackPayload()
                                                .setText(messages.stream().findFirst().orElse(""))
                                                .setAttachments(List.of(new Attachment().setBlocks(List.of(
                                                        new Block()
                                                                .setType(Block.HEADER)
                                                                .setText(new com.ynov.chatbotback.model.response.slack.Text()
                                                                        .setType("plain_text")
                                                                        .setText(movie.getTitle())),
                                                        new Block()
                                                                .setType(Block.IMAGE)
                                                                .setAltText("Affiche de film")
                                                                .setImageUrl(movie.getImageUrl()),
                                                        new Block()
                                                                .setType(Block.SECTION)
                                                                .setText(new com.ynov.chatbotback.model.response.slack.Text()
                                                                        .setText("_Sorti le " + movie.getReleaseDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRANCE)) + "_")),
                                                        new Block()
                                                                .setType(Block.SECTION)
                                                                .setText(new com.ynov.chatbotback.model.response.slack.Text()
                                                                        .setText(movie.getOverview())),
                                                        new Block()
                                                                .setType(Block.ACTIONS)
                                                                .setElements(List.of(new Element()
                                                                        .setText(new TextButton().setText("Voir sur The Movie DB"))
                                                                        .setUrl("https://www.themoviedb.org/movie/" + movie.getId())))
                                                ))))))
                        )
                );
    }
}
