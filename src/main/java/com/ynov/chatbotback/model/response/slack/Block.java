package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Block {

    public static final String SECTION = "section";
    public static final String DIVIDER = "divider";
    public static final String ACTIONS = "actions";
    public static final String HEADER = "header";
    public static final String IMAGE = "image";

    private String type;
    private Text text;
    private Accessory accessory;
    private List<Element> elements;
    @JsonProperty("alt_text")
    private String altText;
    @JsonProperty("image_url")
    private String imageUrl;

}
