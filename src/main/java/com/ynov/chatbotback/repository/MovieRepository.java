package com.ynov.chatbotback.repository;

import com.ynov.chatbotback.model.Genre;
import com.ynov.chatbotback.model.Movie;
import com.ynov.chatbotback.model.moviedb.MovieDBDetailResponse;
import com.ynov.chatbotback.model.moviedb.MovieDBQueryResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MovieRepository {

    private static final Map<Genre, List<String>> movies = Map.of(
            Genre.AVENTURE, List.of("Les Aventuriers de l'arche perdue", "Interstellar", "Uncharted"),
            Genre.CRIME, List.of("Le parrain", "Les évadés", "Ocean's Eleven"),
            Genre.THRILLER, List.of("Le silence des agneaux", "Bullet Train", "Usual Suspects"),
            Genre.ACTION, List.of("Iron Man", "John Wick", "Die Hard, Piège de cristal"),
            Genre.SCIENCE_FICTION, List.of("Inception", "I, Robot", "Avatar"),
            Genre.COMEDIE, List.of("La Grande Vadrouille", "Kaamelott - Premier volet", "Shrek")
    );

    private final RestTemplate restTemplate;

    @Value("${moviedb-key}")
    private String apiKey;

    public List<Movie> findAllByGenre(Genre genre) {
        List<String> moviesName = movies.get(genre);
        if (moviesName == null) {
            return null;
        }
        return moviesName.stream().map(
            it -> restTemplate.getForObject("https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query="+ it + "&language=fr", MovieDBQueryResponse.class).getResults().get(0)
        ).map(it -> new Movie().setId(it.getId()).setOverview(it.getOverview()).setTitle(it.getTitle()).setImageUrl("https://image.tmdb.org/t/p/w500" + it.getPosterPath()))
                .collect(Collectors.toList());
    }

    public Movie findDetailById(String id) {
        MovieDBDetailResponse movieDBDetailResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + apiKey + "&language=fr", MovieDBDetailResponse.class);

        return new Movie()
                .setImageUrl("https://image.tmdb.org/t/p/w500" + movieDBDetailResponse.getPosterPath())
                .setOverview(movieDBDetailResponse.getOverview())
                .setId(movieDBDetailResponse.getId())
                .setTitle(movieDBDetailResponse.getTitle())
                .setReleaseDate(movieDBDetailResponse.getReleaseDate());
    }
}
