package com.ynov.chatbotback.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    SCIENCE_FICTION("Science-fiction"),
    COMEDIE("Comédie"),

    AVENTURE("Aventure"),
    ACTION("Action"),
    THRILLER("Thriller"),
    CRIME("Crime");

    private final String label;

    public static Genre findByLabel(String label) {
        return Arrays.stream(Genre.values())
                .filter(it -> it.label.equals(label))
                .findFirst()
                .orElse(null);
    }
}
