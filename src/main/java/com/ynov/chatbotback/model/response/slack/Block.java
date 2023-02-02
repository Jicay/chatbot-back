package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Block {

    public static final String SECTION = "section";
    public static final String DIVIDER = "divider";
    public static final String ACTIONS = "actions";

    private String type;
    private Text text;
    private Accessory accessory;
    private List<Element> elements;

}
