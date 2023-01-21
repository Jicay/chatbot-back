package com.ynov.chatbotback.model;

import lombok.Data;

@Data
public class Movie {
    private int id;

    private String title;
    private String overview;
    private String imageUrl;
}
