package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Country;
import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.ColumnProperties;
import com.ynov.chatbotback.model.response.Image;
import com.ynov.chatbotback.model.response.Message;
import com.ynov.chatbotback.model.response.Payload;
import com.ynov.chatbotback.model.response.Platform;
import com.ynov.chatbotback.model.response.SimpleResponse;
import com.ynov.chatbotback.model.response.SimpleResponses;
import com.ynov.chatbotback.model.response.TableCard;
import com.ynov.chatbotback.model.response.TableCardCell;
import com.ynov.chatbotback.model.response.TableCardRow;
import com.ynov.chatbotback.model.response.WebhookResponse;
import com.ynov.chatbotback.model.response.slack.Attachment;
import com.ynov.chatbotback.model.response.slack.Block;
import com.ynov.chatbotback.model.response.slack.SlackPayload;
import com.ynov.chatbotback.model.response.slack.Text;
import com.ynov.chatbotback.repository.MessageRepository;
import com.ynov.chatbotback.repository.MovieRepository;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoviePlatformCommand implements WebhookCommand {


    private MessageRepository messageRepository;

    private MovieRepository movieRepository;

    @Override
    public WebhookResponse execute(WebhookRequest request) {
        List<String> messages = messageRepository.findAllByActionAndStep(Step.PLATFORM);

        String movie = (String) request.getQueryResult().getParameters().get("movie");
        var platforms = movieRepository.findPlatform(movie);

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
                                        .setTableCard(new TableCard()
                                                .setTitle(platforms.getTitle())
                                                .setImage(new Image().setImageUri(platforms.getImage()))
                                                .setColumnProperties(List.of(
                                                        new ColumnProperties().setHeader("Pays"),
                                                        new ColumnProperties().setHeader("Platforme")
                                                ))
                                                .setRows(platforms.getPlatforms().entrySet().stream()
                                                        .map(it -> new TableCardRow().setCells(List.of(
                                                                new TableCardCell().setText(it.getKey().getLongLabel()),
                                                                new TableCardCell().setText(it.getValue())
                                                        )))
                                                        .collect(Collectors.toList()))
                                        ),
                                new Message()
                                        .setPlatform(Platform.SLACK)
                                        .setPayload(new Payload().setSlack(new SlackPayload()
                                                .setText(messages.stream().findFirst().orElse(""))
                                                .setAttachments(List.of(new Attachment().setBlocks(List.of(
                                                        new Block()
                                                                .setType(Block.HEADER)
                                                                .setText(new com.ynov.chatbotback.model.response.slack.Text()
                                                                        .setType("plain_text")
                                                                        .setText(platforms.getTitle())),
                                                        new Block()
                                                                .setType(Block.IMAGE)
                                                                .setAltText("Affiche de film")
                                                                .setImageUrl(platforms.getImage()),
                                                        new Block()
                                                                .setType(Block.SECTION)
                                                                .setText(new Text()
                                                                        .setText(computeList(platforms.getPlatforms())))))))))
                        )
                );
    }

    private String computeList(Map<Country, String> platforms) {
        StringBuilder s = new StringBuilder();
        for (Entry<Country, String> p : platforms.entrySet()) {
            s.append(":" + p.getKey().getShortLabel() + ": " + p.getValue() + "\n");
        }
        return s.toString();
    }
}
