package com.thoughtworks.twtheater.movie;


import com.thoughtworks.twtheater.resbody.MovieBrief;
import com.thoughtworks.twtheater.resbody.MovieDetails;
import com.thoughtworks.twtheater.resbody.SearchItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/details/{id}")
    public Optional<MovieDetails> getDetails(@PathVariable("id") int id) {
        return movieService.getMovieDetails(id);
    }

    @GetMapping(value = "/movies")
    public List<MovieBrief> getMovies(@RequestParam String genre, @RequestParam String sorting, @RequestParam int limit) {
        return movieService.getMovieBriefs(genre, sorting, limit);
    }

    @GetMapping(value = "/search")
    public List<SearchItem> searchKey(@RequestParam("keyword") String keywords) {
        return movieService.getSearchResult(keywords);
    }
}