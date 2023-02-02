package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Text {
    private String type = "mrkdwn";
    private String text;
}
