package com.ynov.chatbotback.model.request;

import java.util.Map;
import lombok.Data;

@Data
public class Context {
    private String name;
    private Map<String, String> parameters;
}
