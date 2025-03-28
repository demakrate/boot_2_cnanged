package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.services.RoleService;
import ru.kata.spring.boot_security.demo.db.services.UserService;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class AuthController {
    private final RoleService roleService;
    private final UserService userService;

    public AuthController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                               RedirectAttributes redirectAttributes) {
        try {
            user.setRoles((Set<Role>) roleService.getRoleById(1l));
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Регистрация прошла успешно");
            return "redirect:/";
        } catch (RuntimeException e) {
            System.out.println(e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Пользователь с такой почтой уже существует");
            return "redirect:/";
        }
    }

    @GetMapping("/error")
    public String showLoginForm(Model model) {
        model.addAttribute("errorMessage", "Неверное имя пользователя или пароль.");
        return "index";
    }
}
