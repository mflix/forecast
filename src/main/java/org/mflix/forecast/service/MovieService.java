package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.entity.DirectorEntity;
import org.mflix.forecast.entity.LaunchEntity;
import org.mflix.forecast.entity.MovieDirectorEntity;
import org.mflix.forecast.entity.MovieEntity;
import org.mflix.forecast.entity.MovieStarringEntity;
import org.mflix.forecast.entity.MovieTagEntity;
import org.mflix.forecast.entity.StarringEntity;
import org.mflix.forecast.entity.TagEntity;
import org.mflix.forecast.repository.DirectorRepository;
import org.mflix.forecast.repository.LaunchRepository;
import org.mflix.forecast.repository.MovieDirectorRepository;
import org.mflix.forecast.repository.MovieRepository;
import org.mflix.forecast.repository.MovieStarringRepository;
import org.mflix.forecast.repository.MovieTagRepository;
import org.mflix.forecast.repository.StarringRepository;
import org.mflix.forecast.repository.TagRepository;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final LaunchRepository launchRepository;
    private final MovieDirectorRepository movieDirectorRepository;
    private final MovieStarringRepository movieStarringRepository;
    private final MovieTagRepository movieTagRepository;
    private final StarringRepository starringRepository;
    private final TagRepository tagRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository,
            LaunchRepository launchRepository, MovieDirectorRepository movieDirectorRepository,
            MovieStarringRepository movieStarringRepository, MovieTagRepository movieTagRepository,
            StarringRepository starringRepository, TagRepository tagRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.launchRepository = launchRepository;
        this.movieDirectorRepository = movieDirectorRepository;
        this.movieStarringRepository = movieStarringRepository;
        this.movieTagRepository = movieTagRepository;
        this.starringRepository = starringRepository;
        this.tagRepository = tagRepository;
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
        var directorSet = movieDirectorRepository.findByMovieId(movieId).stream().map((movieDirectorEntity) -> {
            // var directorEntity =
            // directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow();
            // return new DirectorView(directorEntity.getId(), directorEntity.getName());
            return directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow().getName();
        }).collect(Collectors.toSet());

        var launchSet = launchRepository.findByMovieId(movieId).stream().map((launchEntity) -> {
            // return new LaunchView(launchEntity.getId(), launchEntity.getType(),
            // launchEntity.getDate());
            return launchEntity.getType() + " " + launchEntity.getDate();
        }).collect(Collectors.toSet());

        var starringSet = movieStarringRepository.findByMovieId(movieId).stream().map((movieStarringEntity) -> {
            // var starringEntity =
            // starringRepository.findById(movieStarringEntity.getStarringId()).orElseThrow();
            // return new StarringView(starringEntity.getId(), starringEntity.getName());
            return starringRepository.findById(movieStarringEntity.getStarringId()).orElseThrow().getName();
        }).collect(Collectors.toSet());

        var tagSet = movieTagRepository.findByMovieId(movieId).stream().map((movieTagEntity) -> {
            // var tagEntity =
            // tagRepository.findById(movieTagEntity.getTagId()).orElseThrow();
            // return new TagView(tagEntity.getId(), tagEntity.getName());
            return tagRepository.findById(movieTagEntity.getTagId()).orElseThrow().getName();
        }).collect(Collectors.toSet());

        return new MovieView(movieId, movieEnity.getChineseName(), directorSet, movieEnity.getIntroduction(), launchSet,
                movieEnity.getOriginName(), movieEnity.getPosterUrl(), movieEnity.getReleaseDate(),
                movieEnity.getScore(), starringSet, tagSet, movieEnity.getType());

    }
}