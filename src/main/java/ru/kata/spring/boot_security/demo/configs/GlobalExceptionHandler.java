package ru.kata.spring.boot_security.demo.configs;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, JpaObjectRetrievalFailureException.class,})
    public String handleEntityNotFoundException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "message";
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException e, Model model){
        model.addAttribute("message", "Почта уже принадлежит другому пользователю");
        return "message";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        System.out.println(e);
        model.addAttribute("message", "Произошла внутренняя ошибка сервера");
        return "message";
    }
}