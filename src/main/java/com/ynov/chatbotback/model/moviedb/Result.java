package com.ynov.chatbotback.model.moviedb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    private int id;
    private String title;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
}
