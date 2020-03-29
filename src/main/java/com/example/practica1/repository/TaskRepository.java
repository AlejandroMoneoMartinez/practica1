package com.example.practica1.repository;

import java.util.List;
import java.util.Optional;

import com.example.practica1.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();
    List<Task> findAllByUserId(long userId);
    Optional<Task> findById(String name);
}
