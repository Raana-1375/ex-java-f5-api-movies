package com.f5.apimovies.service;

import com.f5.apimovies.entity.Actor;
import com.f5.apimovies.entity.Genre;
import com.f5.apimovies.entity.Movie;
import com.f5.apimovies.entity.Year;
import com.f5.apimovies.repository.ActorRepository;
import com.f5.apimovies.repository.GenreRepository;
import com.f5.apimovies.repository.MovieRepository;
import com.f5.apimovies.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final YearRepository yearRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                         YearRepository yearRepository,
                         GenreRepository genreRepository,
                         ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.yearRepository = yearRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> findByGenre(String genreName) {
        return movieRepository.findByGenres_NameContainingIgnoreCase(genreName);
    }

    public Movie save(Movie movie, Long yearId, Set<Long> genreIds, Set<Long> actorIds) {
        Year year = yearRepository.findById(yearId)
                .orElseThrow(() -> new RuntimeException("Year not found with id: " + yearId));
        movie.setYear(year);

        movie.setGenres(resolveGenres(genreIds));
        movie.setActors(resolveActors(actorIds));

        return movieRepository.save(movie);
    }

    public Movie update(Long id, Movie movieDetails, Long yearId, Set<Long> genreIds, Set<Long> actorIds) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        movie.setTitle(movieDetails.getTitle());
        movie.setDurationMinutes(movieDetails.getDurationMinutes());
        movie.setSynopsis(movieDetails.getSynopsis());

        Year year = yearRepository.findById(yearId)
                .orElseThrow(() -> new RuntimeException("Year not found with id: " + yearId));
        movie.setYear(year);

        movie.setGenres(resolveGenres(genreIds));
        movie.setActors(resolveActors(actorIds));

        return movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    private Set<Genre> resolveGenres(Set<Long> genreIds) {
        Set<Genre> genres = new HashSet<>();
        for (Long genreId : genreIds) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
            genres.add(genre);
        }
        return genres;
    }

    private Set<Actor> resolveActors(Set<Long> actorIds) {
        Set<Actor> actors = new HashSet<>();
        for (Long actorId : actorIds) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new RuntimeException("Actor not found with id: " + actorId));
            actors.add(actor);
        }
        return actors;
    }
}