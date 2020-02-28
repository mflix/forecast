package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.entity.CountryEntity;
import org.mflix.forecast.entity.DirectorEntity;
import org.mflix.forecast.entity.LaunchEntity;
import org.mflix.forecast.entity.MovieCountryEntity;
import org.mflix.forecast.entity.MovieDirectorEntity;
import org.mflix.forecast.entity.MovieEntity;
import org.mflix.forecast.entity.MovieStarringEntity;
import org.mflix.forecast.entity.MovieTagEntity;
import org.mflix.forecast.entity.StarringEntity;
import org.mflix.forecast.entity.TagEntity;
import org.mflix.forecast.repository.CountryRepository;
import org.mflix.forecast.repository.DirectorRepository;
import org.mflix.forecast.repository.LaunchRepository;
import org.mflix.forecast.repository.MovieCountryRepository;
import org.mflix.forecast.repository.MovieDirectorRepository;
import org.mflix.forecast.repository.MovieRepository;
import org.mflix.forecast.repository.MovieStarringRepository;
import org.mflix.forecast.repository.MovieTagRepository;
import org.mflix.forecast.repository.StarringRepository;
import org.mflix.forecast.repository.TagRepository;
import org.mflix.forecast.view.LaunchView;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final DirectorRepository directorRepository;
    private final LaunchRepository launchRepository;
    private final StarringRepository starringRepository;
    private final TagRepository tagRepository;
    private final MovieCountryRepository movieCountryRepository;
    private final MovieDirectorRepository movieDirectorRepository;
    private final MovieStarringRepository movieStarringRepository;
    private final MovieTagRepository movieTagRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, CountryRepository countryRepository,
            DirectorRepository directorRepository, LaunchRepository launchRepository,
            StarringRepository starringRepository, TagRepository tagRepository,
            MovieCountryRepository movieCountryRepository, MovieDirectorRepository movieDirectorRepository,
            MovieStarringRepository movieStarringRepository, MovieTagRepository movieTagRepository) {
        this.movieRepository = movieRepository;
        this.countryRepository = countryRepository;
        this.directorRepository = directorRepository;
        this.launchRepository = launchRepository;
        this.starringRepository = starringRepository;
        this.tagRepository = tagRepository;
        this.movieCountryRepository = movieCountryRepository;
        this.movieDirectorRepository = movieDirectorRepository;
        this.movieStarringRepository = movieStarringRepository;
        this.movieTagRepository = movieTagRepository;
    }

    public MovieView createByBody(MovieView movieView) {
        var chineseName = movieView.getChineseName();
        var introduction = movieView.getIntroduction();
        var originName = movieView.getOriginName();
        var posterUrl = movieView.getPosterUrl();
        var releaseDate = movieView.getReleaseDate();
        var score = movieView.getScore();
        var type = movieView.getType();

        MovieEntity movieEntity = new MovieEntity(chineseName, introduction, originName, posterUrl, releaseDate, score,
                type);
        long movieId = movieRepository.save(movieEntity).getId();

        movieView.getCountryViewSet().stream().forEachOrdered((countryView) -> {
            movieCountryRepository.save(new MovieCountryEntity(movieId, countryRepository.save(countryRepository
                    .findByName(countryView.getName()).orElse(new CountryEntity(countryView.getName()))).getId()));
        });

        movieView.getDirectorViewSet().stream().forEachOrdered((directorView) -> {
            movieDirectorRepository.save(new MovieDirectorEntity(movieId, directorRepository.save(directorRepository
                    .findByName(directorView.getName()).orElse(new DirectorEntity(directorView.getName()))).getId()));
        });

        movieView.getLaunchViewSet().stream().forEachOrdered((launchView) -> {
            launchRepository.save(new LaunchEntity(launchView.getType(), launchView.getDate(), movieId));
        });

        movieView.getStarringViewSet().stream().forEachOrdered((starringView) -> {
            movieStarringRepository.save(new MovieStarringEntity(movieId, starringRepository.save(starringRepository
                    .findByName(starringView.getName()).orElse(new StarringEntity(starringView.getName()))).getId()));
        });

        movieView.getTagViewSet().stream().forEachOrdered((tagView) -> {
            movieTagRepository.save(new MovieTagEntity(movieId,
                    tagRepository
                            .save(tagRepository.findByName(tagView.getName()).orElse(new TagEntity(tagView.getName())))
                            .getId()));
        });

        return movieView;
    }

    public List<MovieView> readAll() {
        return transform(movieRepository.findAll());
    }

    public Page<MovieView> readAllWithPage(Pageable pageable) {
        var movieViewList = transform(movieRepository.findAll(pageable).toList());
        return new PageImpl<>(movieViewList, pageable, movieViewList.size());
    }

    public Page<MovieView> readAllSortByLaunchDateWithPage(Pageable pageable) {
        var movieViewList = launchRepository.findAll(pageable).map((launchEntity) -> {
            var movieEnity = movieRepository.findById(launchEntity.getMovieId()).orElseThrow();
            return new MovieView(movieEnity.getId(), movieEnity.getChineseName(), launchEntity.getDate(),
                    launchEntity.getType(), movieEnity.getOriginName(), movieEnity.getPosterUrl(),
                    movieEnity.getScore());
        }).map((movieView) -> {
            var movieId = movieView.getId();

            var country = movieCountryRepository.findByMovieId(movieId).stream().map((movieCountryEntity) -> {
                return countryRepository.findById(movieCountryEntity.getCountryId()).orElseThrow().getName();
            }).collect(Collectors.joining(" "));

            var director = movieDirectorRepository.findByMovieId(movieId).stream().map((movieDirectorEntity) -> {
                return directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow().getName();
            }).collect(Collectors.joining("/"));

            var starring = movieStarringRepository.findByMovieId(movieId).stream().map((movieStarringEntity) -> {
                return starringRepository.findById(movieStarringEntity.getStarringId()).orElseThrow().getName();
            }).collect(Collectors.joining("/"));

            var cast = director + "/" + starring;

            var tag = movieTagRepository.findByMovieId(movieId).stream().map((movieTagEntity) -> {
                return tagRepository.findById(movieTagEntity.getTagId()).orElseThrow().getName();
            }).collect(Collectors.joining(" "));

            movieView.setCountry(country);
            movieView.setCast(cast);
            movieView.setTag(tag);
            return movieView;
        }).toList();
        return new PageImpl<>(movieViewList, pageable, movieViewList.size());
    }

    public MovieView readById(long id) {
        return transform(movieRepository.findById(id).orElseThrow());
    }

    private List<MovieView> transform(List<MovieEntity> movieEntityList) {
        return movieEntityList.stream().map((movieEnity) -> {
            return transform(movieEnity);
        }).collect(Collectors.toList());
    }

    private MovieView transform(MovieEntity movieEnity) {
        var movieId = movieEnity.getId();

        var country = movieCountryRepository.findByMovieId(movieId).stream().map((movieCountryEntity) -> {
            return countryRepository.findById(movieCountryEntity.getCountryId()).orElseThrow().getName();
        }).collect(Collectors.joining(" "));

        var director = movieDirectorRepository.findByMovieId(movieId).stream().map((movieDirectorEntity) -> {
            return directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow().getName();
        }).collect(Collectors.joining("/"));

        var launchViewSet = launchRepository.findByMovieId(movieId).stream().map((launchEntity) -> {
            return new LaunchView(launchEntity.getId(), launchEntity.getType(), launchEntity.getDate());
        }).collect(Collectors.toSet());

        var starring = movieStarringRepository.findByMovieId(movieId).stream().map((movieStarringEntity) -> {
            return starringRepository.findById(movieStarringEntity.getStarringId()).orElseThrow().getName();
        }).collect(Collectors.joining("/"));

        var tag = movieTagRepository.findByMovieId(movieId).stream().map((movieTagEntity) -> {
            return tagRepository.findById(movieTagEntity.getTagId()).orElseThrow().getName();
        }).collect(Collectors.joining(" "));

        return new MovieView(movieId, movieEnity.getChineseName(), country, director, movieEnity.getIntroduction(),
                launchViewSet, movieEnity.getOriginName(), movieEnity.getPosterUrl(), movieEnity.getReleaseDate(),
                movieEnity.getScore(), starring, tag, movieEnity.getType());

    }
}