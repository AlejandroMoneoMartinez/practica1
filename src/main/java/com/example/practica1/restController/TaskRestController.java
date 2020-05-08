package com.example.practica1.restController;

import com.example.practica1.entity.Task;
import com.example.practica1.entity.User;
import com.example.practica1.exceptions.TaskNotFoundException;
import com.example.practica1.service.TaskService;
import com.example.practica1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// -------------------------------------------------------------------------------------------
// NOTE ---------------------Spring MVC annotations, not specific to spring boot--------------
// -------------------------------------------------------------------------------------------
@RestController // NOTE: Simplifies creating RESTful web services, 
                // the controller now simply returns object data that is written directly to the HTTP response as JSON.
@RequestMapping("/rest") // NOTE: It maps the HTTP requests to the correct handler methods in the controller
// -------------------------------------------------------------------------------------------
// NOTE ---------------------Spring MVC annotations, not specific to spring boot--------------
// -------------------------------------------------------------------------------------------
public class TaskRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody Task task) {
        try {
            User userDb = userService.getUserById(task.getUser().getId());
            userDb.addTask(task);
            userService.saveUser(userDb);
            return new ResponseEntity<>(userDb, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/user/{id}")
    public HttpEntity<?> getUserTasks(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id).getTaskList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //@PreAuthorize("hasAuthority('task')")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllApplications() {
        List<Task> list = taskService.getAllTasks();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }
}
