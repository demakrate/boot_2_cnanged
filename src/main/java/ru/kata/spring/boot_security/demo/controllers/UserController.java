package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.services.RoleService;
import ru.kata.spring.boot_security.demo.db.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;

        this.roleService = roleService;
    }

    @GetMapping(value = "/getAllUsers")
    public String getAll(Model model) {
        if (!userService.getAllUsers().equals(new ArrayList<User>())) {
            model.addAttribute("users", userService.getAllUsers());
            return ("allUsers");
        } else {
            model.addAttribute("message", "Записи отсутствуют");
            return ("message");
        }
    }

    @GetMapping(value = "/getUserByID")
    public String getUser(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            User user = userService.getUserByID(id);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }

        model.addAttribute("roles", roleService.getAllRoles());
        return ("change");
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(111111);
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
        }
        userService.addUser(user);
        model.addAttribute("message", "Пользователь " + user.getName()
                + " успешно добавлен");

        return ("message");
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@ModelAttribute("id") long id, Model model) {

        userService.deleteUser(id);
        model.addAttribute("message", "Пользователь удален");
        return ("message");
    }

    @PostMapping(value = "/changeUser")
    public String changeUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
        }
        userService.changeUser(user);
        model.addAttribute("message", "Пользователь успешно заменён");
        return ("message");
    }
}