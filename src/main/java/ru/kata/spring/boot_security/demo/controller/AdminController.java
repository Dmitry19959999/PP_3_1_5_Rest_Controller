package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin/")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("authUser", user);
        model.addAttribute("allRoleList", roleService.getAllRoles());
        model.addAttribute("newuser", new User());
        return "/admin/admin";
    }

    @PostMapping("/admin")
    public String add(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/admin";
    }

    @PatchMapping("/admin/{id}/updateuser")
    public String update(@PathVariable("id") long id, User user) {
        userService.updateUser(user, id);
        return "redirect:/admin/admin";
    }

    @DeleteMapping("/admin/{id}/deleteuser")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/admin";
    }
}
