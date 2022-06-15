package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/admin/add")
    public String addPage(@ModelAttribute User user, Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "addPage";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam(required = false) String adminRole) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("ROLE_USER"));
        if (adminRole != null && adminRole.equals("ROLE_ADMIN")) {
            roles.add(roleService.findRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String editPage(@PathVariable long id, ModelMap model) {
        User user = userService.findById(id);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.equals(roleService.findRoleByName("ROLE_ADMIN"))){
                model.addAttribute("roleAdmin", true);
            }
        }
        model.addAttribute("user", user);
        return "editPage";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute User user,
                           @RequestParam(required = false) String adminRole) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("ROLE_USER"));
        if (adminRole != null && adminRole.equals("ROLE_ADMIN")) {
            roles.add(roleService.findRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}

