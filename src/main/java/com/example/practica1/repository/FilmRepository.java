package com.example.practica1.repository;

import com.example.practica1.entity.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends CrudRepository<Film, String> {
    @Override
    List<Film> findAll();
}
