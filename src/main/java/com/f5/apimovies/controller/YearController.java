package com.f5.apimovies.controller;

import com.f5.apimovies.entity.Year;
import com.f5.apimovies.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/years")
public class YearController {

    private final YearService yearService;

    @Autowired
    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @GetMapping
    public List<Year> getAll() {
        return yearService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Year> getById(@PathVariable Long id) {
        return yearService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Year> create(@RequestBody Year year) {
        Year saved = yearService.save(year);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Year> update(@PathVariable Long id, @RequestBody Year year) {
        try {
            Year updated = yearService.update(id, year);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        yearService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}