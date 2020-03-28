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

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/ajax/submitTask")
    private String submitTask(@Valid Task task, Errors errors, HttpServletResponse response, Principal principal, Model model) {
        User userLogged = userService.getUserByEmail(principal.getName());
        model.addAttribute("userLogged", userLogged);
        if (errors.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            return "index :: taskForm";
        }
        userLogged.addTask(task);
        userService.saveUser(userLogged);
        model.addAttribute("task", new Task());
        return "index :: taskForm";
    }

    @GetMapping("/ajax/getTaskList")
    private String getTaskList(Principal principal, Model model) {
        model.addAttribute("userLogged", userService.getUserByEmail(principal.getName()));
        return "index :: taskList";
    }
}
