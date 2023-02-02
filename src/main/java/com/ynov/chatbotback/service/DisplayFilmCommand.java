package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Genre;
import com.ynov.chatbotback.model.Movie;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.CarouselSelect;
import com.ynov.chatbotback.model.response.Image;
import com.ynov.chatbotback.model.response.Item;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.Payload;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SelectItemInfo;
import com.ynov.chatbotback.model.response.Text;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.model.response.slack.Accessory;
import com.ynov.chatbotback.model.response.slack.Attachment;
import com.ynov.chatbotback.model.response.slack.Block;
import com.ynov.chatbotback.model.response.slack.Element;
import com.ynov.chatbotback.model.response.slack.SlackPayload;
import com.ynov.chatbotback.model.response.slack.TextButton;
import com.ynov.chatbotback.repository.MessageRepository;
import com.ynov.chatbotback.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DisplayFilmCommand implements WebhookCommand {


    private MessageRepository messageRepository;

    private MovieRepository movieRepository;

    @Override
    public WebhookResponse execute(WebhookRequest request) {
        List<String> messages = messageRepository.findAllByActionAndStep(Step.DISPLAY_FILM);

        String genreInput = (String) request.getQueryResult().getParameters().get("genre");

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
                                                )),
                                new Message()
                                        .setPlatform(Platform.SLACK)
                                        .setPayload(new Payload().setSlack(new SlackPayload()
                                                .setText(messages.get(0))
                                                .setAttachments(List.of(new Attachment().setBlocks(buildBlock(movies))))))
                        )
                );
    }

    private List<Block> buildBlock(List<Movie> movies) {
        List<Block> blocks = new ArrayList<>();

        for (var m : movies) {
            blocks.add(new Block().setType(Block.DIVIDER));
            blocks.add(new Block()
                    .setType(Block.SECTION)
                    .setText(new com.ynov.chatbotback.model.response.slack.Text()
                            .setText("*" + m.getTitle() + "*\n"
                                    + m.getOverview() + "\n"))
                    .setAccessory(new Accessory().setImageUrl(m.getImageUrl()).setAltText("Affiche")));
            blocks.add(new Block()
                    .setType(Block.ACTIONS)
                    .setElements(List.of(new Element()
                            .setText(new TextButton()
                                    .setText(m.getTitle()))
                            .setActionId(String.valueOf(m.getId()))
                            .setValue("Action" + m.getId()))));
        }
        blocks.add(new Block().setType(Block.DIVIDER));

        return blocks;
    }
}
