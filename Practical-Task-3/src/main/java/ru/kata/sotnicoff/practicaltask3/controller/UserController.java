package ru.kata.sotnicoff.practicaltask3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.sotnicoff.practicaltask3.model.User;
import ru.kata.sotnicoff.practicaltask3.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String show(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value = "/edit/{id}")
    public String editPage(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "editPage";
    }

    @PostMapping(value = "/edit")
    public String edit(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/add")
    public String addPage(@ModelAttribute User user) {
        return "addPage";
    }

    @PostMapping(value = "/add")
    public String add(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
