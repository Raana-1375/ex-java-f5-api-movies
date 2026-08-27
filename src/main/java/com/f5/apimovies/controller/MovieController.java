package com.f5.apimovies.controller;

import com.f5.apimovies.dto.MovieRequest;
import com.f5.apimovies.entity.Movie;
import com.f5.apimovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // 1) Obtener todas las películas
    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    // 2) Obtener una película mediante su Id
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3) Añadir una película
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setSynopsis(request.getSynopsis());

        try {
            Movie saved = movieService.save(movie, request.getYearId(), request.getGenreIds(), request.getActorIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 4) Actualizar los datos de una película
    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody MovieRequest request) {
        Movie movieDetails = new Movie();
        movieDetails.setTitle(request.getTitle());
        movieDetails.setDurationMinutes(request.getDurationMinutes());
        movieDetails.setSynopsis(request.getSynopsis());

        try {
            Movie updated = movieService.update(id, movieDetails, request.getYearId(), request.getGenreIds(), request.getActorIds());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5) Eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 6) Endpoint extra: buscar por título o género (findBy)
    @GetMapping("/search")
    public List<Movie> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {

        if (title != null) {
            return movieService.findByTitle(title);
        } else if (genre != null) {
            return movieService.findByGenre(genre);
        } else {
            return movieService.findAll();
        }
    }
}