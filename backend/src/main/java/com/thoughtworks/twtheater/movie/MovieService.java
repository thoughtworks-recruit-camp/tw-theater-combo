package com.thoughtworks.twtheater.movie;

import com.thoughtworks.twtheater.resbody.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<MovieDetails> getMovieDetails(int id) {
        return movieRepository.findById(id)
                .map(movie -> DataUtil.makeDetails(movie, getRecommends(movie)));
    }

    public List<SearchItem> getSearchResult(String keyword) {
        return movieRepository.findByKeyword(String.format("%%%s%%", keyword));
    }

    public List<MovieBrief> getMovieBriefs(String genres, String sorting, int limit) {
        return (limit == 0
                ? findAll(String.format("%%%s%%", genres), sorting)
                : findWithLimit(String.format("%%%s%%", genres), sorting, limit))
                .stream()
                .map(DataUtil::makeBrief)
                .collect(Collectors.toList());
    }

    private List<Movie> findAll(String genres, String sorting) {
        if (genres.equals("%ALL%")) {
            switch (sorting) {
                case "top":
                    return movieRepository.findAllMovies();
                case "random":
                    return movieRepository.findAllMoviesRandom();
                default:
                    return new ArrayList<>();
            }
        }
        switch (sorting) {
            case "top":
                return movieRepository.findByGenre(genres);
            case "random":
                return movieRepository.findByGenreRandom(genres);
            default:
                return new ArrayList<>();
        }
    }

    private List<Movie> findWithLimit(String genre, String sorting, int limit) {
        if (genre.equals("%ALL%")) {
            switch (sorting) {
                case "top":
                    return movieRepository.findAllMoviesLimit(limit);
                case "random":
                    return movieRepository.findAllMoviesLimitRandom(limit);
                default:
                    return new ArrayList<>();
            }
        }
        switch (sorting) {
            case "top":
                return movieRepository.findByGenreLimit(genre, limit);
            case "random":
                return movieRepository.findByGenreLimitRandom(genre, limit);
            default:
                return new ArrayList<>();
        }
    }

    public List<Recommend> getRecommends(Movie movie) {
        List<String> topGenres = Arrays.stream(movie.getGenres().split(","))
                .limit(3)
                .collect(Collectors.toList());
        int selfId = movie.getId();
        List<Recommend> recommends = topGenres.stream()
                .map(movieRepository::getRecommends)
                .flatMap(Collection::stream)
                .filter(rec -> rec.getId() != selfId)
                .distinct()
                .collect(Collectors.toList());
        Collections.shuffle(recommends);
        return recommends.stream()
                .limit(6)
                .collect(Collectors.toList());
    }
}