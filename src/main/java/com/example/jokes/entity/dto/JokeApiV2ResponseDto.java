package com.example.jokes.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokeApiV2ResponseDto {
    private String id;
    private String category;
    private String type;
    private String setup;
    private String delivery;
    private String joke;
    private String lang;
    private Boolean error;
}
