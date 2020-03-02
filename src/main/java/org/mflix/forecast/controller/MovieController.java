package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.MovieService;
import org.mflix.forecast.view.MovieView;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
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
        movieView = movieService.createByBody(movieView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, movieView);
    }

    @GetMapping("/page/")
    public ResponseEntity<ResponseView> getAllWithPage(Pageable pageable) {
        var movieViewPage = movieService.readAllWithPage(pageable);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, movieViewPage);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ResponseView> getById(@PathVariable long id) {
        var movieView = movieService.readById(id);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, movieView);
    }

    @GetMapping("/launch/page/")
    public ResponseEntity<ResponseView> getAllByLaunchWithPage(Pageable pageable,
            @RequestParam(required = false) String version, @RequestParam(required = false) String subtype) {
        var movieLaunchView = movieService.readAllSortByLaunchDateWithPage(pageable, version, subtype);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, movieLaunchView);
    }
}