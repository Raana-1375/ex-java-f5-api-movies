package com.f5.apimovies.service;

import com.f5.apimovies.entity.Year;
import com.f5.apimovies.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YearService {

    private final YearRepository yearRepository;

    @Autowired
    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public List<Year> findAll() {
        return yearRepository.findAll();
    }

    public Optional<Year> findById(Long id) {
        return yearRepository.findById(id);
    }

    public Year save(Year year) {
        return yearRepository.save(year);
    }

    public Year update(Long id, Year yearDetails) {
        Year year = yearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Year not found with id: " + id));
        year.setValue(yearDetails.getValue());
        return yearRepository.save(year);
    }

    public void deleteById(Long id) {
        yearRepository.deleteById(id);
    }
}
