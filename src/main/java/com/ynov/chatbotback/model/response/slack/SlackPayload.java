package com.ynov.chatbotback.model.response.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SlackPayload {
    private String text;
    private List<Attachment> attachments;
}
