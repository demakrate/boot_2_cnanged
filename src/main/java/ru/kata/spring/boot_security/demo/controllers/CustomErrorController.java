package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    return "errors/404";
                default:
                    return "errors/500";
            }
        }
        return "errors/500";
    }


    @GetMapping("/errors/403")
    public String accessDenied() {
        return "errors/403";
    }

    @GetMapping("/errors/401")
    public String unauthorized() {
        return "errors/401";
    }

    @GetMapping("/errors/404")
    public String notFound() {
        return "errors/404";
    }

    @GetMapping("/errors/500")
    public String internalServerError() {
        return "errors/500";
    }
}
