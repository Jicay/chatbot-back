package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class QuickReplies {
    private String text;
    private List<String> quickReplies;
}
