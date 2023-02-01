package com.ynov.chatbotback.model.moviedb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Platform {
    @JsonProperty("provider_name")
    private String providerName;
}
