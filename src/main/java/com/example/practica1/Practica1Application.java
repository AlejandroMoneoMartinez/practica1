package com.example.practica1;

import com.example.practica1.entity.Role;
import com.example.practica1.entity.User;
import com.example.practica1.entity.UserRole;
import com.example.practica1.repository.RoleRepository;
import com.example.practica1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Practica1Application {

    private static Logger log = LoggerFactory.getLogger(Practica1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Practica1Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner runner() {
        return new CommandLineRunner() {
            @Override //SPRING BOOT MAIN
            public void run(String... args) throws Exception {
                if (roleRepository.findAll().isEmpty()) {
                    List<Role> roleList = new ArrayList<>();
                    roleList.add(new Role(Role.ROLE_ADMIN));
                    roleList.add(new Role(Role.ROLE_USER));
                    roleRepository.saveAll(roleList);
                }

                if (userRepository.findAll().isEmpty()) {
                    User userAdmin = new User("Omar", "Ben", "omaxlive3@gmail.com", "64840828", true);
                    userAdmin.setPassword("asedasd123123123");
                    userAdmin.addUserRole(new UserRole(roleRepository.findByName(Role.ROLE_ADMIN).orElseThrow(() -> new Exception("cannot find role " + Role.ROLE_ADMIN))));
                    userRepository.save(userAdmin);
                    log.info("Creado usuario admin");
                }
            }
        };
    }

}
