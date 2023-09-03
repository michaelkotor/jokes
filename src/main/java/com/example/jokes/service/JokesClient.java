package com.example.jokes.service;

import com.example.jokes.exception.ServiceNotAvailableException;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;


public interface JokesClient {

    String getRandomJoke();

    String getJokesByCategory(String category);

    String getJokesByQuery(String query);

    default Mono<Throwable> handle5xxServerError(ClientResponse clientResponse) {
        throw new ServiceNotAvailableException("The API is not available at the moment. Please try again later");
    }
}
