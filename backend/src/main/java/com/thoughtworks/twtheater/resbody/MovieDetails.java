package com.thoughtworks.twtheater.resbody;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MovieDetails {
    private final Integer id;
    private final String title;
    @JsonProperty("original_title")
    private final String originalTitle;
    private final double rating;
    private final String genres;
    private final String year;
    private final String pubdates;
    private final String summary;
    private final String durations;
    private final String photos;
    private final String album;
    private final String cast;
    private final List<Recommend> recommends;
}
