package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
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
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("editUser");
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@Validated @ModelAttribute("newUser") User user,
                          @RequestParam String authority) {
        Set<Role> roles = new HashSet<>();
        if (authority.equals("ADMIN")) {
            roles.add(roleService.findRoleByName("ROLE_USER"));
            roles.add(roleService.findRoleByName("ROLE_ADMIN"));
        }
        roles.add(roleService.findRoleByName("ROLE_USER"));

        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("editUser") User user,
                           @RequestParam(value = "index", required = false) Long[] identifiers,
                           @RequestParam Long id) {
        Set<Role> roles = new HashSet<>();
        if (identifiers != null) {
            for (Long roleId : identifiers) {
                if (roleId == 2) {
                    roles.add(roleService.findRoleById(1L));
                    roles.add(roleService.findRoleById(2L));
                }
                roles.add(roleService.findRoleById(roleId));
            }
        } else {
            roles.addAll(userService.findById(id).getRoles());
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/delete")
    public String deleteUser(@RequestParam long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}

