package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.component.DoubanComponent;
import org.mflix.forecast.entity.CountryEntity;
import org.mflix.forecast.entity.DirectorEntity;
import org.mflix.forecast.entity.LaunchEntity;
import org.mflix.forecast.entity.MovieCountryEntity;
import org.mflix.forecast.entity.MovieDirectorEntity;
import org.mflix.forecast.entity.MovieEntity;
import org.mflix.forecast.entity.MovieCastEntity;
import org.mflix.forecast.entity.MovieGenreEntity;
import org.mflix.forecast.entity.CastEntity;
import org.mflix.forecast.entity.GenreEntity;
import org.mflix.forecast.repository.CountryRepository;
import org.mflix.forecast.repository.DirectorRepository;
import org.mflix.forecast.repository.LaunchRepository;
import org.mflix.forecast.repository.MovieCountryRepository;
import org.mflix.forecast.repository.MovieDirectorRepository;
import org.mflix.forecast.repository.MovieRepository;
import org.mflix.forecast.repository.MovieCastRepository;
import org.mflix.forecast.repository.MovieGenreRepository;
import org.mflix.forecast.repository.CastRepository;
import org.mflix.forecast.repository.GenreRepository;
import org.mflix.forecast.view.LaunchView;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final DoubanComponent doubanComponent;
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final DirectorRepository directorRepository;
    private final LaunchRepository launchRepository;
    private final CastRepository castRepository;
    private final GenreRepository genreRepository;
    private final MovieCountryRepository movieCountryRepository;
    private final MovieDirectorRepository movieDirectorRepository;
    private final MovieCastRepository movieCastRepository;
    private final MovieGenreRepository movieGenreRepository;

    @Autowired
    public MovieService(DoubanComponent doubanComponent, MovieRepository movieRepository,
            CountryRepository countryRepository, DirectorRepository directorRepository,
            LaunchRepository launchRepository, CastRepository castRepository, GenreRepository genreRepository,
            MovieCountryRepository movieCountryRepository, MovieDirectorRepository movieDirectorRepository,
            MovieCastRepository movieCastRepository, MovieGenreRepository movieGenreRepository) {
        this.doubanComponent = doubanComponent;
        this.movieRepository = movieRepository;
        this.countryRepository = countryRepository;
        this.directorRepository = directorRepository;
        this.launchRepository = launchRepository;
        this.castRepository = castRepository;
        this.genreRepository = genreRepository;
        this.movieCountryRepository = movieCountryRepository;
        this.movieDirectorRepository = movieDirectorRepository;
        this.movieCastRepository = movieCastRepository;
        this.movieGenreRepository = movieGenreRepository;
    }

    public MovieView createByBody(MovieView movieView) {
        doubanComponent.extract(movieView);
        MovieEntity movieEntity = new MovieEntity(movieView.getImageUrl(), movieView.getOriginalTitle(),
                movieView.getPubdate(), movieView.getRating(), movieView.getSubtype(), movieView.getSummary(),
                movieView.getTitle());
        long movieId = movieRepository.save(movieEntity).getId();
        movieView.getCastViewSet().stream().forEachOrdered((castView) -> {
            movieCastRepository.save(new MovieCastEntity(movieId,
                    castRepository.save(
                            castRepository.findByName(castView.getName()).orElse(new CastEntity(castView.getName())))
                            .getId()));
        });
        movieView.getCountryViewSet().stream().forEachOrdered((countryView) -> {
            movieCountryRepository.save(new MovieCountryEntity(movieId, countryRepository.save(countryRepository
                    .findByName(countryView.getName()).orElse(new CountryEntity(countryView.getName()))).getId()));
        });
        movieView.getDirectorViewSet().stream().forEachOrdered((directorView) -> {
            movieDirectorRepository.save(new MovieDirectorEntity(movieId, directorRepository.save(directorRepository
                    .findByName(directorView.getName()).orElse(new DirectorEntity(directorView.getName()))).getId()));
        });
        movieView.getGenreViewSet().stream().forEachOrdered((genreView) -> {
            movieGenreRepository.save(new MovieGenreEntity(movieId, genreRepository
                    .save(genreRepository.findByName(genreView.getName()).orElse(new GenreEntity(genreView.getName())))
                    .getId()));
        });
        movieView.getLaunchViewSet().stream().forEachOrdered((launchView) -> {
            launchRepository.save(new LaunchEntity(launchView.getVersion(), launchView.getDate(), movieId));
        });
        return movieView;
    }

    public Page<MovieView> readAllWithPage(Pageable pageable) {
        var movieEntityPage = movieRepository.findAll(pageable);
        var movieViewList = transform(movieEntityPage.toList());
        return new PageImpl<>(movieViewList, pageable, movieViewList.size());
    }

    public Page<MovieView> readAllSortByLaunchDateWithPage(Pageable pageable, String version, String subtype) {
        Page<LaunchEntity> launchEntityPage;
        if (version == null || "all".equals(version)) {
            launchEntityPage = launchRepository.findAll(pageable);
        } else {
            launchEntityPage = launchRepository.findAllByVersion(version, pageable);
        }
        var movieViewList = launchEntityPage.map((launchEntity) -> {
            var movieEntity = movieRepository.findById(launchEntity.getMovieId()).orElseThrow();
            return new MovieView(movieEntity.getId(), movieEntity.getImageUrl(), movieEntity.getOriginalTitle(),
                    movieEntity.getRating(), movieEntity.getSubtype(), movieEntity.getTitle(), launchEntity.getDate(),
                    launchEntity.getVersion());
        }).filter((movieView) -> {
            if (subtype == null || "all".equals(subtype)) {
                return true;
            }
            return subtype.equals(movieView.getSubtype());
        }).map((movieView) -> {
            var movieId = movieView.getId();

            var cast = movieCastRepository.findByMovieId(movieId).stream().map((movieCastEntity) -> {
                return castRepository.findById(movieCastEntity.getCastId()).orElseThrow().getName();
            }).collect(Collectors.joining("/"));
            var country = movieCountryRepository.findByMovieId(movieId).stream().map((movieCountryEntity) -> {
                return countryRepository.findById(movieCountryEntity.getCountryId()).orElseThrow().getName();
            }).collect(Collectors.joining(" "));
            var director = movieDirectorRepository.findByMovieId(movieId).stream().map((movieDirectorEntity) -> {
                return directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow().getName();
            }).collect(Collectors.joining("/"));
            var genre = movieGenreRepository.findByMovieId(movieId).stream().map((movieGenreEntity) -> {
                return genreRepository.findById(movieGenreEntity.getGenreId()).orElseThrow().getName();
            }).collect(Collectors.joining(" "));
            var castDirector = director + "/" + cast;

            movieView.setCastDirector(castDirector);
            movieView.setCountry(country);
            movieView.setGenre(genre);
            return movieView;
        }).toList();
        return new PageImpl<>(movieViewList, pageable, movieViewList.size());
    }

    public MovieView readById(long id) {
        return transform(movieRepository.findById(id).orElseThrow());
    }

    private List<MovieView> transform(List<MovieEntity> movieEntityList) {
        return movieEntityList.stream().map((movieEntity) -> {
            return transform(movieEntity);
        }).collect(Collectors.toList());
    }

    private MovieView transform(MovieEntity movieEntity) {
        var movieId = movieEntity.getId();

        var country = movieCountryRepository.findByMovieId(movieId).stream().map((movieCountryEntity) -> {
            return countryRepository.findById(movieCountryEntity.getCountryId()).orElseThrow().getName();
        }).collect(Collectors.joining(" "));

        var director = movieDirectorRepository.findByMovieId(movieId).stream().map((movieDirectorEntity) -> {
            return directorRepository.findById(movieDirectorEntity.getDirectorId()).orElseThrow().getName();
        }).collect(Collectors.joining("/"));

        var launchViewSet = launchRepository.findByMovieId(movieId).stream().map((launchEntity) -> {
            return new LaunchView(launchEntity.getId(), launchEntity.getDate(), launchEntity.getVersion());
        }).collect(Collectors.toSet());

        var cast = movieCastRepository.findByMovieId(movieId).stream().map((movieCastEntity) -> {
            return castRepository.findById(movieCastEntity.getCastId()).orElseThrow().getName();
        }).collect(Collectors.joining("/"));

        var genre = movieGenreRepository.findByMovieId(movieId).stream().map((movieGenreEntity) -> {
            return genreRepository.findById(movieGenreEntity.getGenreId()).orElseThrow().getName();
        }).collect(Collectors.joining(" "));

        return new MovieView(movieId, movieEntity.getImageUrl(), launchViewSet, movieEntity.getOriginalTitle(),
                movieEntity.getPubdate(), movieEntity.getRating(), movieEntity.getSummary(), movieEntity.getTitle(),
                cast, director, country, genre);
    }
}