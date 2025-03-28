package ru.kata.spring.boot_security.demo.db.services;

import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByID(long id);

    void addUser(User user);

    void deleteUser(long idr);

    void changeUser(User user);
}
