import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.Key;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Key
    private int id;
    private String title;
    private String originalTitle;
    private BigDecimal rating;
    private String genres;
    private String year;
    private String pubdates;
    private String image;
    private String summary;
    private String durations;
    private String photos;
    private String album;
    private String cast;

    public void setId(Object id) {
        this.id = (int) id;
    }

    public void setTitle(Object title) {
        this.title = (String) title;
    }

    public void setOriginalTitle(Object originalTitle) {
        this.originalTitle = (String) originalTitle;
    }

    public void setRating(Object rating) {
        this.rating = BigDecimal.valueOf(Double.parseDouble(rating.toString()));
    }

    public void setGenres(Object genres) {
        this.genres = (String) genres;
    }

    public void setYear(Object year) {
        this.year =(String)  year;
    }

    public void setPubdates(Object pubdates) {
        this.pubdates = (String) pubdates;
    }

    public void setImage(Object image) {
        this.image = (String) image;
    }

    public void setSummary(Object summary) {
        this.summary = (String) summary;
    }

    public void setDurations(Object durations) {
        this.durations = (String) durations;
    }

    public void setPhotos(Object photos) {
        this.photos = (String) photos;
    }

    public void setAlbum(Object album) {
        this.album = (String) album;
    }

    public void setCast(Object cast) {
        this.cast = (String) cast;
    }
}
