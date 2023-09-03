package com.example.jokes.service.impl;

import com.example.jokes.entity.dto.JokeApiV2ResponseDto;
import com.example.jokes.exception.NoSuchCategoryException;
import com.example.jokes.service.JokesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Primary
@Service
public class JokeApiV2Client implements JokesClient {

    private static final String BASE_URL = "https://v2.jokeapi.dev/joke";
    private final WebClient webClient = WebClient.create(BASE_URL);

    @Override
    public String getRandomJoke() {
        JokeApiV2ResponseDto result = webClient
                .get()
                .uri("/Any")
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(JokeApiV2ResponseDto.class)
                .block();
        log.info(result.toString());
        return mapJokeApiV2ResponseDtoToJoke(result);
    }

    @Override
    public String getJokesByCategory(String category) {
        JokeApiV2ResponseDto result;
        result = webClient
                .get()
                .uri("/" + category)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(JokeApiV2ResponseDto.class).block();

        log.info(result.toString());
        return mapJokeApiV2ResponseDtoToJoke(result);
    }


    @Override
    public String getJokesByQuery(String query) {
        JokeApiV2ResponseDto result = webClient
                .get()
                .uri("/Any?contains=" + query)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(JokeApiV2ResponseDto.class)
                .block();

        log.info(result.toString());
        return mapJokeApiV2ResponseDtoToJoke(result);
    }

    private String mapJokeApiV2ResponseDtoToJoke(JokeApiV2ResponseDto jokeApiV2ResponseDto) {
        // For some reason API returns 200 OK even if there is no such category
        if(jokeApiV2ResponseDto.getError()) {
            throw new NoSuchCategoryException("Invalid category");
        }

        if (jokeApiV2ResponseDto.getType().equals("single")) {
            return jokeApiV2ResponseDto.getJoke();
        } else {
            return jokeApiV2ResponseDto.getSetup() + " " + jokeApiV2ResponseDto.getDelivery();
        }
    }
}
