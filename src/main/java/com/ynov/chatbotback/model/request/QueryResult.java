package com.ynov.chatbotback.model.request;

import java.util.Map;
import lombok.Data;

@Data
public class QueryResult {
    private String queryText;
    private String languageCode;
    private String action;
    private Map<String, Object> parameters;
}