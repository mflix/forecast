package org.mflix.forecast.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mflix.forecast.properties.ApplicationProperties;
import org.mflix.forecast.view.CastView;
import org.mflix.forecast.view.CountryView;
import org.mflix.forecast.view.DirectorView;
import org.mflix.forecast.view.GenreView;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Component
public class DoubanComponent {
    private final ApplicationProperties applicationProperties;

    @Autowired
    public DoubanComponent(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public MovieView extract(MovieView movieView) {
        var subject = movieView.getSubject();
        var uri = UriComponentsBuilder.newInstance().scheme("https").host("api.douban.com")
                .path("/v2/movie/subject/" + subject).queryParam("apikey", applicationProperties.getDoubanApiKey())
                .build().toUri();
        var responseType = new RestTemplate().getForEntity(uri, ResponseType.class).getBody();
        transform(movieView, responseType);
        return movieView;
    }

    private void transform(MovieView movieView, ResponseType responseType) {
        var castViewSet = responseType.getCasts().stream().map(cast -> new CastView(cast.getName()))
                .collect(Collectors.toSet());
        movieView.setCastViewSet(castViewSet);
        var countryViewSet = responseType.getCountries().stream().map(country -> new CountryView(country))
                .collect(Collectors.toSet());
        movieView.setCountryViewSet(countryViewSet);
        var directorViewSet = responseType.getDirectors().stream().map(director -> new DirectorView(director.getName()))
                .collect(Collectors.toSet());
        movieView.setDirectorViewSet(directorViewSet);
        var genreViewSet = responseType.getGenres().stream().map((genre) -> {
            return new GenreView(genre);
        }).collect(Collectors.toSet());
        movieView.setGenreViewSet(genreViewSet);
        movieView.setImageUrl(responseType.getImage().getLarge());
        movieView.setOriginalTitle(responseType.getOriginalTitle());
        var pubdate = responseType.getPubdates().stream().map((date) -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                return new Date();
            }
        }).min((a, b) -> {
            return (int) (a.getTime() - b.getTime());
        }).orElse(null);
        movieView.setPubdate(pubdate);
        movieView.setRating(responseType.getRating().getAverage());
        movieView.setSubtype(responseType.getSubtype());
        movieView.setSummary(responseType.getSummary());
        movieView.setTitle(responseType.getTitle());
    }

    @Data
    @JsonInclude(Include.NON_EMPTY)
    private static class ResponseType {
        private List<Cast> casts;
        private List<String> countries;
        private List<Director> directors;
        private List<String> genres;
        @JsonProperty("images")
        private Image image;
        @JsonProperty("original_title")
        private String originalTitle;
        private List<String> pubdates;
        private Rating rating;
        private String subtype;
        private String summary;
        private String title;

        @Data
        @JsonInclude(Include.NON_EMPTY)
        private static class Rating {
            private double average;
        }

        @Data
        @JsonInclude(Include.NON_EMPTY)
        private static class Image {
            private String large;
        }

        @Data
        @JsonInclude(Include.NON_EMPTY)
        private static class Cast {
            private String name;
        }

        @Data
        @JsonInclude(Include.NON_EMPTY)
        private static class Director {
            private String name;
        }
    }
}