package com.example.jokes.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * This file needed only if we want to define custom time to live for different caches.
 */
@EnableCaching
@Configuration
public class CacheConfig {

    public static final String JOKES_RANDOM_CACHE = "randomJokes";
    public static final String JOKES_BY_CATEGORY_CACHE = "jokesByCategory";
    public static final String JOKES_BY_QUERY_CACHE = "jokesByQuery";

    @Bean
    public CaffeineCache cacheRandomJokes() {
        return new CaffeineCache(JOKES_RANDOM_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.SECONDS)
                        .build());
    }

    @Bean
    public CaffeineCache cacheJokesByCategory() {
        return new CaffeineCache(JOKES_BY_CATEGORY_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .recordStats()
                        .build());
    }

    @Bean
    public CaffeineCache cacheJokesByQuery() {
        return new CaffeineCache(JOKES_BY_QUERY_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .recordStats()
                        .build());
    }
}
