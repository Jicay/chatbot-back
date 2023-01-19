package com.ynov.chatbotback.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByActionAndStep(String action, Step step);
}
