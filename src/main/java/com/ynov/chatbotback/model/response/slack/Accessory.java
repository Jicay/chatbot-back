package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Accessory {
    private String type = "image";
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("alt_text")
    private String altText;
}
