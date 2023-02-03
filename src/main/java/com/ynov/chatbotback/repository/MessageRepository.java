package com.ynov.chatbotback.repository;

import com.ynov.chatbotback.model.Step;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MessageRepository {

    private static final Map<Step, List<String>> messages = Map.of(
            Step.ASK_GENRE, List.of("Très bien. Quel genre veux-tu voir ?"),
            Step.ASK_GENRE_FALLBACK, List.of("Je n'ai pas compris quel genre tu recherches. Peux-tu repréciser ou sélectionner un genre que je te propose ?"),
            Step.DISPLAY_FILM, List.of("OK, voici ce que je te propose :"),
            Step.DISPLAY_DETAIL, List.of("Voici des informations plus détaillées sur le film"),
            Step.PLATFORM, List.of("Le film est disponible sur ces plateformes"),
            Step.DEFAULT, List.of("Je n'ai pas compris ce que tu demandes, peux-tu reformuler ?")
    );

    public List<String> findAllByActionAndStep(Step step) {
        return messages.get(step);
    }
}
