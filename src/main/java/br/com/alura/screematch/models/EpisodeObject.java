package br.com.alura.screematch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EpisodeObject {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rating;
    private LocalDate releaseDate;

    public EpisodeObject(Integer season, Episode episode){
        this.season = season;
        this.title = episode.title();
        this.episode = episode.episode();

        try {
            this.rating = Double.valueOf(episode.rating());
        }
        catch (NumberFormatException exception){
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episode.releaseDate());
        }
        catch (DateTimeParseException ex) {
           this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        try{
            this.rating = rating;
        }
        catch (NumberFormatException exception){
            this.rating = 0.0;
        }
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        try {
            this.releaseDate = releaseDate;
        }
        catch (DateTimeParseException ex) {
            this.releaseDate = null;
        }
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title + '\'' +
                ", episode=" + episode +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }
}
