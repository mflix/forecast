package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class MovieView {
    private long id;
    @NotBlank
    private String chineseName;
    @NotEmpty
    @JsonProperty("directors")
    private Set<DirectorView> directorViewSet;
    private Set<String> directorSet;
    @NotBlank
    private String introduction;
    @NotEmpty
    @JsonProperty("launches")
    private Set<LaunchView> launchViewSet;
    private Set<String> launchSet;
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
    private Set<String> starringSet;
    @NotEmpty
    @JsonProperty("tags")
    private Set<TagView> tagViewSet;
    private Set<String> tagSet;
    @NotBlank
    private String type;

    public MovieView() {
    }

    public MovieView(long id, @NotBlank String chineseName, Set<String> directorSet, @NotBlank String introduction,
            Set<String> launchSet, @NotBlank String originName, @NotBlank String posterUrl, @NotNull Date releaseDate,
            @NotNull Double score, Set<String> starringSet, Set<String> tagSet, @NotBlank String type) {
        this.id = id;
        this.chineseName = chineseName;
        this.directorSet = directorSet;
        this.introduction = introduction;
        this.launchSet = launchSet;
        this.originName = originName;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.score = score;
        this.starringSet = starringSet;
        this.tagSet = tagSet;
        this.type = type;
    }
}