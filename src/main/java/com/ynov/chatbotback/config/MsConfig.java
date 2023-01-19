package com.ynov.chatbotback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = "com.ynov.chatbotback.repository"
)
@Configuration
public class MsConfig {

}
