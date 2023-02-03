package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Element {
    private String type = "button";
    private TextButton text;
    private String value;
    private String url;
    @JsonProperty("action_id")
    private String actionId;
}
