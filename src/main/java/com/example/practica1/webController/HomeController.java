package com.example.practica1.webController;

import com.example.practica1.entity.Task;
import com.example.practica1.entity.User;
import com.example.practica1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping({"/index", "/"})
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping
    private String index(Principal principal, Model model) {
        model.addAttribute("userLogged", userService.getUserByEmail(principal.getName()));
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping
    private String indexPost(@Valid Task task, Errors errors, Principal principal, Model model) {
        User userLogged = userService.getUserByEmail(principal.getName());
        model.addAttribute("userLogged", userLogged);
        if (errors.hasErrors())
            return "index";
        userLogged.addTask(task);
        userService.saveUser(userLogged);
        model.addAttribute("task", new Task());
        return "index";
    }
}
