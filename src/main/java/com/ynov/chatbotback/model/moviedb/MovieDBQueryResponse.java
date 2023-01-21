package com.ynov.chatbotback.model.moviedb;

import java.util.List;
import lombok.Data;

@Data
public class MovieDBQueryResponse {
    private List<Result> results;
}
