package com.example.practica1.restController;

import com.example.practica1.entity.Task;
import com.example.practica1.entity.User;
import com.example.practica1.repository.TaskRepository;
import com.example.practica1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/tasks")
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
    private TaskRepository taskRepository;

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

    @GetMapping("/{id}")
    public HttpEntity<?> getTask(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(taskRepository.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
