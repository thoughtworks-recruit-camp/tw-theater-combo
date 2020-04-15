package com.thoughtworks.twtheater.resbody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieBrief {
    private final int id;
    private final String title;
    private final double rating;
    private final String genres;
    private final String year;
    private final String summary;
}
