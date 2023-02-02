package com.ynov.chatbotback.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BasicCard {
    private String title;
    private String subtitle;
    private String formattedText;
    private Image image;
    private List<Button> buttons;
}
