package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.MovieService;
import org.mflix.forecast.view.MovieView;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private final ResponseComponent responseComponent;
    private final MovieService movieService;

    @Autowired
    public MovieController(ResponseComponent responseComponent, MovieService movieService) {
        this.movieService = movieService;
        this.responseComponent = responseComponent;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseView> postByBody(@Valid @RequestBody MovieView movieView) {
        movieService.createByBody(movieView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }
}