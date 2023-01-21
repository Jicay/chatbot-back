package com.ynov.chatbotback.model.response;

import java.util.List;
import lombok.Data;

@Data
public class SelectItemInfo {
    private String key;
    private List<String> synonyms;
}
