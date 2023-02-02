package com.ynov.chatbotback.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Message {

    private Platform platform;

    private Text text;
    private Suggestions suggestions;

    private SimpleResponses simpleResponses;
    private CarouselSelect carouselSelect;

    private BasicCard basicCard;

    private TableCard tableCard;

    private Payload payload;
}
