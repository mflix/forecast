package org.mflix.forecast.service;

import org.mflix.forecast.repository.MovieRepository;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public MovieView createByBody(MovieView movieView) {
        return movieView;
    }
}