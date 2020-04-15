package com.thoughtworks.twtheater.movie;


import com.thoughtworks.twtheater.movie.Movie;
import com.thoughtworks.twtheater.resbody.Recommend;
import com.thoughtworks.twtheater.resbody.SearchItem;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    @Query(value
            = "SELECT id, title, original_title, rating, genres, year, summary, cast "
            + "FROM movie "
            + "WHERE title LIKE :keyword OR original_title LIKE :keyword")
    List<SearchItem> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT * FROM movie WHERE genres LIKE :genres ORDER BY rating DESC LIMIT :limit ")
    List<Movie> findByGenreLimit(@Param("genres") String genres, @Param("limit") int limit);

    @Query("SELECT * FROM movie WHERE genres LIKE :genres ORDER BY rand() LIMIT :limit ")
    List<Movie> findByGenreLimitRandom(@Param("genres") String genres, @Param("limit") int limit);

    @Query("SELECT * FROM movie ORDER BY rating DESC LIMIT :limit")
    List<Movie> findAllMoviesLimit(@Param("limit") int limit);

    @Query("SELECT * FROM movie ORDER BY rand() LIMIT :limit")
    List<Movie> findAllMoviesLimitRandom(@Param("limit") int limit);

    @Query("SELECT * FROM movie WHERE genres LIKE :genres ORDER BY rating DESC")
    List<Movie> findByGenre(@Param("genres") String genres);

    @Query("SELECT * FROM movie WHERE genres LIKE :genres ORDER BY rand()")
    List<Movie> findByGenreRandom(@Param("genres") String genres);

    @Query("SELECT * FROM movie ORDER BY rating DESC ")
    List<Movie> findAllMovies();

    @Query("SELECT * FROM movie ORDER BY rand() DESC")
    List<Movie> findAllMoviesRandom();

    @Query("SELECT id, title FROM movie WHERE genres LIKE :genres ORDER BY rand()")
    List<Recommend> getRecommends(@Param("genres") String genre);
}
