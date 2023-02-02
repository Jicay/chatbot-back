package com.ynov.chatbotback.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ynov.chatbotback.model.response.slack.SlackPayload;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Payload {
    private SlackPayload slack;
}
