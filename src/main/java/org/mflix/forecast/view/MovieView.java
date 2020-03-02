package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class MovieView {
    private String subject;

    private long id;
    @JsonProperty("casts")
    private Set<CastView> castViewSet;
    @JsonProperty("countries")
    private Set<CountryView> countryViewSet;
    @JsonProperty("directors")
    private Set<DirectorView> directorViewSet;
    @JsonProperty("genres")
    private Set<GenreView> genreViewSet;
    private String imageUrl;
    @JsonProperty("launches")
    private Set<LaunchView> launchViewSet;
    private String originalTitle;
    private Date pubdate;
    private double rating;
    private String subtype;
    private String summary;
    private String title;

    private String cast;
    private String castDirector;
    private String director;
    private String country;
    private Date date;
    private String genre;
    private String version;

    public MovieView() {
    }

    public MovieView(long id, String imageUrl, String originalTitle, double rating, String subtype, String title,
            Date date, String version) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.subtype = subtype;
        this.title = title;
        this.date = date;
        this.version = version;
    }

    public MovieView(long id, String imageUrl, Set<LaunchView> launchViewSet, String originalTitle, Date pubdate,
            double rating, String summary, String title, String cast, String director, String country, String genre) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.launchViewSet = launchViewSet;
        this.originalTitle = originalTitle;
        this.pubdate = pubdate;
        this.rating = rating;
        this.summary = summary;
        this.title = title;
        this.cast = cast;
        this.director = director;
        this.country = country;
        this.genre = genre;
    }
}