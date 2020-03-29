package com.example.practica1.service;

import com.example.practica1.entity.Task;
import com.example.practica1.exceptions.TaskNotFoundException;
import com.example.practica1.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listTasks() {
        return (List<Task>) taskRepository.findAll();
    }
    
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElse(null);
        // Optional<Task> optionalTask = taskRepository.findById(id);
        // if(optionalTask.isPresent())
        //   return optionalTask.get();
        // else
        //   throw new TaskNotFoundException("Task Not Found");

    }

}