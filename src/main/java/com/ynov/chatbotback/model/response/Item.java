package com.ynov.chatbotback.model.response;

import lombok.Data;

@Data
public class Item {
    private String title;
    private String description;
    private Image image;
    private SelectItemInfo info;
}
