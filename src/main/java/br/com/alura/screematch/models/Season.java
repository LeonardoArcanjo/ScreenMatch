package br.com.alura.screematch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Season(@JsonAlias({"Season"}) int season,
                     @JsonAlias("Episodes") List<Episode> episodes) {
}
