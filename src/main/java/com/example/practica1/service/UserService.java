package com.example.practica1.service;

import com.example.practica1.entity.Role;
import com.example.practica1.entity.User;
import com.example.practica1.entity.UserRole;
import com.example.practica1.exceptions.EmailAlreadyExistsException;
import com.example.practica1.repository.RoleRepository;
import com.example.practica1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public void encodePassword(User user){
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    public User createUser(User user) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail()))
            throw new EmailAlreadyExistsException(user.getEmail());

        this.encodePassword(user);
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone(), true);
        newUser.addUserRole(new UserRole(roleRepository.findByName(Role.ROLE_USER).orElse(null)));
        return userRepository.save(newUser);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElse(null);
    }
}
