package com.ynov.chatbotback.repository;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    private String action;
    private String response;

    @Enumerated(EnumType.STRING)
    private Step step;
}
