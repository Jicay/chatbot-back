package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class BasicCard {
    private String title;
    private String subtitle;
    private String formattedText;
    private Image image;
    private List<Button> buttons;
}
