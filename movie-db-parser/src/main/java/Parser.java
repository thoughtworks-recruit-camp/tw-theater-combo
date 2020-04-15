import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Parser {
    public static void main(String[] args) throws IOException, SQLException {
        String path = Optional.ofNullable(
                Parser.class.getClassLoader().getResource("moviesDb.json"))
                .map(URL::getFile)
                .orElseThrow(FileNotFoundException::new);

        ObjectMapper mapper = new ObjectMapper();
        List<MovieRaw> movieRawList = Arrays.asList(
                mapper.readValue(new File(path), MovieRaw[].class)
        );
        List<Movie> movies = movieRawList.stream().map(
                movieRaw -> new Movie(
                        movieRaw.getId(),
                        movieRaw.getTitle(),
                        movieRaw.getOriginalTitle(),
                        movieRaw.getRating(),
                        String.join(",", movieRaw.getGenres()),
                        movieRaw.getYear(),
                        String.join(",", movieRaw.getPubdates()),
                        movieRaw.getImage(),
                        movieRaw.getSummary(),
                        String.join(",", movieRaw.getDurations()),
                        String.join(",", movieRaw.getPhotos()),
                        movieRaw.getAlbum(),
                        String.join(",", movieRaw.getCast())
                )
        ).collect(Collectors.toList());
        Function<Movie, String> getTitle = Movie::getTitle;
        Function<Movie, String> getOriginalTitle = Movie::getOriginalTitle;
        Function<Movie, String> getGenres = Movie::getGenres;
        Function<Movie, String> getYear = Movie::getYear;
        Function<Movie, String> getPubdates = Movie::getPubdates;
        Function<Movie, String> getImage = Movie::getImage;
        Function<Movie, String> getSummary = Movie::getSummary;
        Function<Movie, String> getDurations = Movie::getDurations;
        Function<Movie, String> getPhotos = Movie::getPhotos;
        Function<Movie, String> getAlbum = Movie::getAlbum;
        Function<Movie, String> getCast = Movie::getCast;
        getMaxLength(movies, getTitle, "title");
        getMaxLength(movies, getOriginalTitle, "original_title");
        getMaxLength(movies, getGenres, "genres");
        getMaxLength(movies, getYear, "year");
        getMaxLength(movies, getPubdates, "pubdates");
        getMaxLength(movies, getImage, "image");
        getMaxLength(movies, getSummary, "summary");
        getMaxLength(movies, getDurations, "durations");
        getMaxLength(movies, getPhotos, "photos");
        getMaxLength(movies, getAlbum, "album");
        getMaxLength(movies, getCast, "cast");
//        Properties properties = new Properties();
//        properties.load(Parser.class.getResourceAsStream("jdbc.properties"));
//        try (Connection connection = DriverManager.getConnection(
//                properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
//             MovieRepository movieRepository = new MovieRepository()) {
//            movieRepository.setConnection(connection);
//            movieRepository.saveAll(movies);
//        }
    }
    private static void getMaxLength(List<Movie> movies, Function<Movie, String> getterFunction, String columnName) {
        System.out.printf("'%s' maxLength: %d\n", columnName,
                movies.stream().map(getterFunction).map(String::length).max(Integer::compareTo).orElse(0));
    }
}
