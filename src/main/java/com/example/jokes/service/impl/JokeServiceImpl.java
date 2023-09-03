package com.example.jokes.service.impl;

import com.example.jokes.entity.JokeDto;
import com.example.jokes.service.JokesClient;
import com.example.jokes.service.JokesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.example.jokes.config.CacheConfig.JOKES_BY_CATEGORY_CACHE;
import static com.example.jokes.config.CacheConfig.JOKES_BY_QUERY_CACHE;
import static com.example.jokes.config.CacheConfig.JOKES_RANDOM_CACHE;

@Slf4j
@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokesService {

    /**
     * Well, here caching is tricky. We can cache,
     * but the cache should live not that long,
     * otherwise jokes would not be random for users.
     * see @CacheConfig
     * @return random joke
     */
    @Override
    @Cacheable(JOKES_RANDOM_CACHE)
    public JokeDto getRandomJoke() {
        String randomJoke = jokesClient.getRandomJoke();
        return new JokeDto(randomJoke);
    }

    private final JokesClient jokesClient;

    @Override
    @Cacheable(JOKES_BY_CATEGORY_CACHE)
    public JokeDto getJokesByCategory(String category) {
        String randomJoke = jokesClient.getJokesByCategory(category);
        return new JokeDto(randomJoke);
    }

    @Override
    @Cacheable(JOKES_BY_QUERY_CACHE)
    public JokeDto getJokesByQuery(String query) {
        String foundJoke = jokesClient.getJokesByQuery(query);
        return new JokeDto(foundJoke);
    }
}
