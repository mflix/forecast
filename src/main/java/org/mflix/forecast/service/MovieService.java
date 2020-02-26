package org.mflix.forecast.service;

import org.mflix.forecast.properties.ApplicationProperties;
import org.mflix.forecast.repository.MovieRepository;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final ApplicationProperties applicationProperties;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(ApplicationProperties applicationProperties, MovieRepository movieRepository) {
        this.applicationProperties = applicationProperties;
        this.movieRepository = movieRepository;
    }

    public MovieView createByBody(MovieView movieView) {
        return movieView;
    }
}