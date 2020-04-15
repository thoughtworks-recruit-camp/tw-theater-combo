package com.thoughtworks.twtheater.resbody;

import com.thoughtworks.twtheater.movie.Movie;

import java.util.List;

public final class DataUtil {
    private DataUtil() {
    }
    public static MovieDetails makeDetails(Movie movie, List<Recommend> recommends) {
        return new MovieDetails(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginal_title(),
                movie.getRating(),
                movie.getGenres(),
                movie.getYear(),
                movie.getPubdates(),
                movie.getSummary(),
                movie.getDurations(),
                movie.getPhotos(),
                movie.getAlbum(),
                movie.getCast(),
                recommends
        );
    }

    public static MovieBrief makeBrief(Movie movie) {
        return new MovieBrief(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getGenres(),
                movie.getYear(),
                movie.getSummary()
        );
    }
}
