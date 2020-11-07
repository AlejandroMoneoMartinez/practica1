package com.example.practica1.restController;

import com.example.practica1.entity.Film;
import com.example.practica1.repository.FilmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/films")
public class FilmRestController {

    @Autowired
    private FilmRepository filmRepository;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<Film>> getAll() {
        return new ResponseEntity<>(filmRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable("id") String id) {
        return filmRepository.findById(id).map(film -> new ResponseEntity<>(film, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> save(@RequestBody String data) throws JsonProcessingException {
        List<Film> filmList = Arrays.asList(new ObjectMapper().readValue(data, Film[].class));
        filmList.forEach(film -> {
            if (!filmRepository.existsById(film.getId()))
                filmRepository.save(film);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        filmRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
