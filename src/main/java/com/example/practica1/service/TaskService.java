package com.example.practica1.service;

import com.example.practica1.entity.Task;
import com.example.practica1.exceptions.TaskNotFoundException;
import com.example.practica1.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Task task) {
        Task taskdb = this.getTaskById(task.getId());
        taskdb.setName(task.getName());
        taskdb.setDone(task.isDone());
        return taskRepository.save(taskdb);
    }
}