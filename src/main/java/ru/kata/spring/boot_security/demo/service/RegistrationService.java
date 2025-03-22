package ru.kata.spring.boot_security.demo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;

@Service
public class RegistrationService {
    private final UserRepository repository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(UserRepository repository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        if (repository.findByMail(user.getMail()).isPresent()) {
            throw new DataIntegrityViolationException("Пользователь с таким email уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = rolesRepository.findByRoleName("USER").orElseThrow(()
                -> new RuntimeException("Роль USER не найдена"));
        user.setRoles(Collections.singleton(role));
        repository.save(user);
    }
}
