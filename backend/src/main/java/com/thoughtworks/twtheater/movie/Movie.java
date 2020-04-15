package com.thoughtworks.twtheater.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("movie")
public class Movie {
    @Id
    private Integer id;
    private String title;
    private String original_title;
    private double rating;
    private String genres;
    private String year;
    private String pubdates;
    private String summary;
    private String durations;
    private String photos;
    private String album;
    private String cast;

    public Movie(Integer id, String title, String original_title, double rating, String genres, String year, String pubdates, String summary, String durations, String photos, String album, String cast) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.rating = rating;
        this.genres = genres;
        this.year = year;
        this.pubdates = pubdates;
        this.summary = summary;
        this.durations = durations;
        this.photos = photos;
        this.album = album;
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPubdates() {
        return pubdates;
    }

    public void setPubdates(String pubdates) {
        this.pubdates = pubdates;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", title='" + title + '\'' +
                ", original_title='" + original_title + '\'' +
                ", rating=" + rating +
                ", genres='" + genres + '\'' +
                ", year='" + year + '\'' +
                ", pubdates='" + pubdates + '\'' +
                ", summary='" + summary + '\'' +
                ", durations='" + durations + '\'' +
                ", photos='" + photos + '\'' +
                ", album='" + album + '\'' +
                ", cast='" + cast;
    }
}