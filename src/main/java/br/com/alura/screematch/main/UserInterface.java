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

//        List<Episode> allEpisodes = new ArrayList<>();
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

        // print all Episodes (in EpisodesObject way)
        // episodes.forEach(System.out::println);

        // Show top 5 episodes by rating
        /*allEpisodes.stream()
                .filter(ep -> !ep.rating().equals("N/A"))                   // remove N/A rating
                .peek(e -> System.out.println("after filter: " + e))        // show result of last stream operation
                .sorted(Comparator.comparing(Episode::rating).reversed())           // decrescent sorted
                .limit(10)
                .map(e -> e.title().toUpperCase())
                .forEach(System.out::println);*/

        // Show episodes list after a year typed by user
        /*System.out.println("A partir de que ano voce deseja ver os episodios mais bem avaliados? ");
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
                ));*/

        // Found an episode typed by user
        /*System.out.println("Type the episode are you find?");
        var searchedEpisode = reader.nextLine();

        // String handler
        StringBuilder myEp = new StringBuilder();
        String[] words = searchedEpisode.split(" ");

        for (String word : words){
            if (!word.isEmpty()){
                myEp.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        var ep = myEp.toString().trim();        // clear the last ` ` append in the last word

        Optional<EpisodeObject> myFavoriteEpisode = episodes.stream()
                .filter(e -> e.getTitle().contains(ep))
                .findFirst();    // Return the first element in a search predicate, it`s a final stream operation and return an Optional<T>

        if (myFavoriteEpisode.isPresent())
            System.out.println("My search episode found: " + myFavoriteEpisode);
        else
            System.out.println("Episode not found!");*/

        // Get Rating per Season
        /*Map<Integer, Double> ratingPerSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(EpisodeObject::getSeason,
                        Collectors.averagingDouble(EpisodeObject::getRating)));         // Create a Map Object (key-value)
                                                                                        // using Collectors
        System.out.println(ratingPerSeason);*/

        // Getting new statistics
        /*DoubleSummaryStatistics statistics = episodes.stream()              // Class to get many statistics operations
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(EpisodeObject::getRating));

        System.out.println("Statistics: " + statistics);                    // Show some statistics (length, sum, min, max, average, max)
        System.out.println("rating average: " + statistics.getAverage());
        System.out.println("Worst episode by rating: " + statistics.getMin());
        System.out.println("Best episode by rating: " + statistics.getMax());*/

        // Getting statistics per season
        Map<Integer, DoubleSummaryStatistics> statisticsPerSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(EpisodeObject::getSeason,
                        Collectors.summarizingDouble(EpisodeObject::getRating)));

        statisticsPerSeason.forEach((s, stat) -> System.out.println("Season: " + s
                + " statistics: " + stat ));
    }
}
