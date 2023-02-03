package com.ynov.chatbotback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    FRANCE("France", "fr"),
    USA("Etats-Unis", "us"),
    BELGIQUE("Belgique", "drapeau-be"),
    ROYAUME_UNI("Royaume-Uni", "gb"),
    JAPON("Japon", "jp");

    private final String longLabel;
    private final String shortLabel;

}
