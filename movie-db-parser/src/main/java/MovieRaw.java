import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRaw {
    private int id;
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    private BigDecimal rating;
    private String[] genres;
    private String year;
    private String[] pubdates;
    private String image;
    private String summary;
    private String[] durations;
    private String[] photos;
    private String album;
    private String[] cast;
}
