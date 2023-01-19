package com.ynov.chatbotback.repository;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    private Integer id;
    private String action;
    private String response;

    @Enumerated(EnumType.STRING)
    private Step step;
}
