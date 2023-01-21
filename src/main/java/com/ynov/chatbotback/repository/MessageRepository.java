package com.ynov.chatbotback.repository;

import com.ynov.chatbotback.model.Step;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MessageRepository {

    private static final Map<String, List<String>> messages = Map.of(
            "movie.advice|GENRE_QUESTION", List.of("Très bien. Quel genre veux-tu voir ?"),
            "movie.advice|AFFICHAGE_RESULTAT", List.of("OK, voici ce que je te propose :")
    );

    public List<String> findAllByActionAndStep(String action, Step step) {
        return messages.get(action + "|" + step);
    }
}
