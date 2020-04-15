package com.thoughtworks.twtheater.resbody;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SearchItem {
    private int id;
    private String title;
    private String original_title;
    private BigDecimal rating;
    private String genres;
    private String year;
    private String summary;
    private String cast;
}
