package br.com.alura.screematch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episode(@JsonAlias({"Title", "title"}) String title,
                      @JsonAlias("Episode") int episode,
                      @JsonAlias({"Released"}) String releaseDate,
                      @JsonAlias({"imdbRating"}) String rating) {
}
