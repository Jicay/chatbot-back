package com.ynov.chatbotback.model.response;

import lombok.Data;

@Data
public class Button {
    private String title;
    private OpenUriAction openUriAction;
}
