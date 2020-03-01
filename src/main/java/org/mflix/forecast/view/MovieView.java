package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MovieView {
    private String doubanUrl;
    private String chineseName;
    @JsonProperty("countries")
    private Set<CountryView> countryViewSet;
    @JsonProperty("directors")
    private Set<DirectorView> directorViewSet;
    private String introduction;
    @JsonProperty("launches")
    private Set<LaunchView> launchViewSet;
    private String originName;
    private String posterUrl;
    private Date releaseDate;
    private Double score;
    @JsonProperty("starrings")
    private Set<StarringView> starringViewSet;
    @JsonProperty("tags")
    private Set<TagView> tagViewSet;
    private String type;

    private long id;
    private String cast;
    private String country;
    private String director;
    private Date launchDate;
    private String launchType;
    private String starring;
    private String tag;

    public MovieView() {
    }

    public MovieView(long id, @NotBlank String chineseName, String country, String director,
            @NotBlank String introduction, @NotEmpty Set<LaunchView> launchViewSet, @NotBlank String originName,
            @NotBlank String posterUrl, @NotNull Date releaseDate, @NotNull Double score, String starring, String tag,
            @NotBlank String type) {
        this.id = id;
        this.chineseName = chineseName;
        this.country = country;
        this.director = director;
        this.introduction = introduction;
        this.launchViewSet = launchViewSet;
        this.originName = originName;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.score = score;
        this.starring = starring;
        this.tag = tag;
        this.type = type;
    }

    public MovieView(long id, @NotBlank String chineseName, String cast, String country,
            @NotEmpty Set<LaunchView> launchViewSet, @NotBlank String originName, @NotBlank String posterUrl,
            @NotNull Double score, String tag) {
        this.id = id;
        this.chineseName = chineseName;
        this.cast = cast;
        this.country = country;
        this.launchViewSet = launchViewSet;
        this.originName = originName;
        this.posterUrl = posterUrl;
        this.score = score;
        this.tag = tag;
    }

    public MovieView(long id, @NotBlank String chineseName, Date launchDate, String launchType,
            @NotBlank String originName, @NotBlank String posterUrl, @NotNull Double score, String type) {
        this.id = id;
        this.chineseName = chineseName;
        this.launchDate = launchDate;
        this.launchType = launchType;
        this.originName = originName;
        this.posterUrl = posterUrl;
        this.score = score;
        this.type = type;
    }
}