package com.example.practica1.resolver;

import com.example.practica1.entity.Task;
import com.example.practica1.repository.TaskRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TaskQuery implements GraphQLQueryResolver {

  @Autowired
  private TaskRepository taskRepository;

  public List<Task> findAllTasksByUserId(long userId) {
    return taskRepository.findAllByUserId(userId);
  }

  public long countTasks() {
    return taskRepository.count();
  }
}