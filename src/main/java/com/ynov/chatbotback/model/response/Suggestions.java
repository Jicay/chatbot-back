package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class Suggestions {
    private List<Suggestion> suggestions;
}
