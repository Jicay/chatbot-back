package com.ynov.chatbotback.repository;

import com.ynov.chatbotback.model.Step;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MessageRepository {

    private static final Map<Step, List<String>> messages = Map.of(
            Step.ASK_GENRE, List.of("Très bien. Quel genre veux-tu voir ?"),
            Step.DISPLAY_FILM, List.of("OK, voici ce que je te propose :"),
            Step.DISPLAY_DETAIL, List.of("Voici le détail"),
            Step.DEFAULT, List.of("Je n'ai pas compris ce que tu demandes, peux-tu reformuler ?")
    );

    public List<String> findAllByActionAndStep(Step step) {
        return messages.get(step);
    }
}
