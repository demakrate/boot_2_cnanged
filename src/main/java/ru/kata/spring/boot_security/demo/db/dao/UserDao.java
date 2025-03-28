package ru.kata.spring.boot_security.demo.db.dao;


import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    User getUserByID(long id);

    void addUser(User user);

    void deleteUser(long id);

    void changeUser(User user);

    User findByUsernameWithRoles(String username);

    Optional<User> findByEmail(String email);
}
