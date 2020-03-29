package com.example.practica1.mutator;

// import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.practica1.entity.Task;
import com.example.practica1.repository.TaskRepository;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
class Mutation implements GraphQLMutationResolver {

  private TaskRepository taskRepository;

  public Mutation(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Task newTask(String name, Boolean done) {
    Task task = new Task(name, done);
    taskRepository.save(task);
    return task;
  }

  public boolean deleteTask(Long id) {
    taskRepository.deleteById(id);
    return true;
  }
  
  // updateTaskName
}