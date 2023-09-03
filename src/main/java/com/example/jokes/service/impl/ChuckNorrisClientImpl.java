package com.example.jokes.service.impl;

import com.example.jokes.entity.dto.ChuckNorrisResponseDto;
import com.example.jokes.exception.NoSuchCategoryException;
import com.example.jokes.service.JokesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Not finished implementation of JokesClient as was unstable
 */
@Service
@Deprecated
@RequiredArgsConstructor
public class ChuckNorrisClientImpl implements JokesClient {

    private final String BASE_URL = "https://api.chucknorris.io/jokes";
    private final WebClient webClient = WebClient.create(BASE_URL);

    public String getRandomJoke() {
        ChuckNorrisResponseDto randomJoke = webClient.get()
                .uri("/random")
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(ChuckNorrisResponseDto.class)
                .block();

        return randomJoke.getValue();
    }


    public String getJokesByCategory(String category) {
        ChuckNorrisResponseDto jokeByCategory =  webClient.get()
                .uri("/random?category={category}", category)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new NoSuchCategoryException("No such category");
                })
                .bodyToMono(ChuckNorrisResponseDto.class)
                .block();

        return jokeByCategory.getValue();
    }


    public String getJokesByQuery(String query) {
        ChuckNorrisResponseDto jokeByQuery = webClient.get()
                .uri("/search?query={query}", query)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(ChuckNorrisResponseDto.class)
                .block();

        return jokeByQuery.getValue();
    }
}
