package com.example.jokes.contoller;

import com.example.jokes.entity.JokeDto;
import com.example.jokes.exception.RateLimiterException;
import com.example.jokes.service.JokesService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/v1/jokes")
public class JokesController {

    private final JokesService jokesService;
    private final Bucket bucket;

    public JokesController(JokesService jokesService) {
        this.jokesService = jokesService;

        // Will be added to random jokes endpoint only for easier testing
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/search")
    public ResponseEntity<JokeDto> getJokeByQuery(@RequestParam String query) {
        return ResponseEntity.ok(jokesService.getJokesByQuery(query));
    }

    @GetMapping("/category")
    public ResponseEntity<JokeDto> getJokesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(jokesService.getJokesByCategory(category));
    }

    @GetMapping("/random")
    public ResponseEntity<JokeDto> getRandomJoke() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(jokesService.getRandomJoke());
        } else {
            throw new RateLimiterException("Too many requests");
        }
    }
}
