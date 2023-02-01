package com.ynov.chatbotback.model.moviedb;

import java.util.Map;
import lombok.Data;

@Data
public class MovieDBProviderResponse {
    private int id;
    private Map<String, PlatformCountry> results;
}
