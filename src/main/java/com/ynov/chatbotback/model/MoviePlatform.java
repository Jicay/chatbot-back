package com.ynov.chatbotback.model;

import java.util.Map;
import lombok.Data;

@Data
public class MoviePlatform {
    private String title;
    private String image;
    private Map<String, String> platforms;
}
