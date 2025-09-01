package br.com.alura.screematch.main;

import br.com.alura.screematch.models.Episode;
import br.com.alura.screematch.models.EpisodeObject;
import br.com.alura.screematch.models.Season;
import br.com.alura.screematch.models.Serie;
import br.com.alura.screematch.service.ConsumoAPI;
import br.com.alura.screematch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {
    private final Scanner reader = new Scanner(System.in);
    private final ConsumoAPI apiConsumer = new ConsumoAPI();
    private final DataConverter converter = new DataConverter();
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apiKey=6585022c";

    public void showMenu(){
        System.out.println("Type the tv serie to search: ");
        var tvSerieName = reader.nextLine();

        var requestAddress = ADDRESS  + tvSerieName.replace(" ","+") + API_KEY;
        var data = apiConsumer.GetData(requestAddress);

        Serie serie = converter.GetData(data, Serie.class);

        System.out.println("Serie " + tvSerieName + ":");
        System.out.println(serie);

        List<Episode> allEpisodes = new ArrayList<>();
        List<EpisodeObject> episodes = new ArrayList<>();

        // Show Season and episodes by seasons data
        for (int season = 1; season <= serie.totalSeasons(); season++)
        {
            requestAddress = ADDRESS + tvSerieName.replace(" ","+") + "&Season=" + season + API_KEY;

            data = apiConsumer.GetData(requestAddress);

            Season seasonTemp = converter.GetData(data, Season.class);

            var currentSeason = season;

//            allEpisodes.addAll(seasonTemp.episodes());

            episodes.addAll(
                    seasonTemp.episodes().stream()
                    .map(ep -> new EpisodeObject(currentSeason, ep))
                    .collect(Collectors.toList()));
        }

        System.out.println("Best episodes of " + tvSerieName);
        // Show top 5 episodes by rating
        allEpisodes.stream()
                .filter(ep -> !ep.rating().equals("N/A"))           // remove N/A rating
                .sorted(Comparator.comparing(Episode::rating).reversed())   // decrescent sorted
                .limit(5)
                .forEach(System.out::println);

        episodes.forEach(System.out::println);

        System.out.println("A partir de que ano voce deseja ver os episodios mais bem avaliados? ");
        var year = reader.nextInt();
        reader.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(ep -> ep.getReleaseDate() != null
                        && ep.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                " Episode: " + e.getTitle() +
                                " Release Date: " + e.getReleaseDate().format(dateFormater)
                ));
    }
}
