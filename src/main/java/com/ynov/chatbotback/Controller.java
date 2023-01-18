package com.ynov.chatbotback;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @GetMapping("/hello")
    public String get() {
        return "Hello world !";
    }

    @PostMapping("/webhook")
    public WebhookResponse matchIntent(@RequestBody WebhookRequest request) {
        log.info("{}", request);
        return new WebhookResponse()
                .setFulfillmentMessages(List.of(
                                new Message()
                                        .setText(
                                                new Text()
                                                        .setText(
                                                                List.of("Quel film ?")
                                                        )
                                        )
                        )
                );
    }

}
