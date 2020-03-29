
package com.example.practica1.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.practica1.entity.Task;
import com.example.practica1.repository.TaskRepository;

import org.springframework.stereotype.Component;

@Component
class Query implements GraphQLQueryResolver {

  private TaskRepository taskRepository;

  public Query(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Iterable<Task> findAllTasks() {
    return taskRepository.findAll();
  }

  public long countTasks() {
    return taskRepository.count();
  }
}