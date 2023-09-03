package com.example.jokes.service;

import com.example.jokes.entity.JokeDto;

import java.util.List;

public interface JokesService {

    JokeDto getRandomJoke();

    JokeDto getJokesByCategory(String category);

    JokeDto getJokesByQuery(String query);
}
