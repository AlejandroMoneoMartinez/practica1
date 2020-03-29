package com.example.practica1.mutator;

import com.example.practica1.entity.Task;
import com.example.practica1.service.TaskService;
import com.example.practica1.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TaskMutation implements GraphQLMutationResolver {

  @Autowired
  private UserService userService;

  @Autowired
  private TaskService taskService;


  public Task createTask(long userId, String name, Boolean done) {
    return userService.createUserTask(userId, name, done);
  }

  public Task updateTask(Task task) {
    return taskService.updateTask(task);
  }

  public boolean deleteTask(long id) {
    return userService.deleteUserTask(id);
  }
}