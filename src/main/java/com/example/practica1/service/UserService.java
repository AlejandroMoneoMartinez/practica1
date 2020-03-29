package com.example.practica1.service;

import com.example.practica1.entity.Role;
import com.example.practica1.entity.Task;
import com.example.practica1.entity.User;
import com.example.practica1.entity.UserRole;
import com.example.practica1.exceptions.EmailAlreadyExistsException;
import com.example.practica1.repository.RoleRepository;
import com.example.practica1.repository.TaskRepository;
import com.example.practica1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    public void encodePassword(User user){
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail()))
            throw new EmailAlreadyExistsException(user.getEmail());

        this.encodePassword(user);
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone(), true);
        newUser.addUserRole(new UserRole(roleRepository.findByName(Role.ROLE_USER).orElse(null)));
        return userRepository.save(newUser);
    }

    public Task createUserTask(long userId, String name, boolean done) {
        User userDb = this.getUserById(userId);
        userDb.addTask(new Task(name, done));
        userRepository.save(userDb);
        return userDb.getTaskList().get(userDb.getTaskList().size()-1);
    }

    public boolean deleteUserTask(long taskId) {
        Optional<Task> taskDb = taskRepository.findById(taskId);
        if (!taskDb.isPresent()) return false;
        User userDb = taskDb.get().getUser();
        userDb.removeTask(taskDb.get());
        userRepository.save(userDb);
        return true;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElse(null);
    }
}
