package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class MovieView {
    private long id;
    @NotBlank
    private String chineseName;
    @NotEmpty
    @JsonProperty("countries")
    private Set<CountryView> countryViewSet;
    private String country;
    @NotEmpty
    @JsonProperty("directors")
    private Set<DirectorView> directorViewSet;
    private String director;
    @NotBlank
    private String introduction;
    @NotEmpty
    @JsonProperty("launches")
    private Set<LaunchView> launchViewSet;
    @NotBlank
    private String originName;
    @NotBlank
    private String posterUrl;
    @NotNull
    private Date releaseDate;
    @NotNull
    private Double score;
    @NotEmpty
    @JsonProperty("starrings")
    private Set<StarringView> starringViewSet;
    private String starring;
    @NotEmpty
    @JsonProperty("tags")
    private Set<TagView> tagViewSet;
    private String tag;
    @NotBlank
    private String type;

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
}