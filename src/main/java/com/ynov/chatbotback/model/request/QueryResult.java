package com.ynov.chatbotback.model.request;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class QueryResult {
    private String queryText;
    private String languageCode;
    private String action;
    private Map<String, Object> parameters;
    private List<Context> outputContexts;
}
