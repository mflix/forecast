package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.MovieService;
import org.mflix.forecast.view.MovieView;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie/")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ResponseComponent responseComponent;

    @PostMapping("/")
    public ResponseEntity<ResponseView> postByBody(@Valid @RequestBody MovieView movieView) {
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }
}