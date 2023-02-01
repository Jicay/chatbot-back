package com.ynov.chatbotback.service;

import com.ynov.chatbotback.model.Step;
import com.ynov.chatbotback.model.request.WebhookRequest;
import com.ynov.chatbotback.model.response.WebhookResponse;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WebhookInvocator {
    private Map<Step, WebhookCommand> commands;

    public WebhookInvocator(
            AskGenreCommand askGenreCommand,
            DisplayFilmCommand displayFilmCommand,
            DisplayDetailCommand displayDetailCommand,
            MoviePlatformCommand moviePlatformCommand,
            DefaultCommand defaultCommand
    ) {
        commands = Map.of(
                Step.ASK_GENRE, askGenreCommand,
                Step.DISPLAY_FILM, displayFilmCommand,
                Step.DISPLAY_DETAIL, displayDetailCommand,
                Step.PLATFORM, moviePlatformCommand,
                Step.DEFAULT, defaultCommand
        );
    }

    public WebhookResponse execute(WebhookRequest request) {
        String action = request.getQueryResult().getAction();

        Step step = Step.DEFAULT;

        switch (action) {
            case "movie.advice" -> {
                if (StringUtils.hasText((String) request.getQueryResult().getParameters().get("genre"))) {
                    step = Step.DISPLAY_FILM;
                } else {
                    step = Step.ASK_GENRE;
                }
            }
            case "movie.advice.genre" -> step = Step.DISPLAY_FILM;
            case "movie.advice.details" -> step = Step.DISPLAY_DETAIL;
            case "movie.platform" -> step = Step.PLATFORM;
        }
        return commands.get(step).execute(request);
    }
}
