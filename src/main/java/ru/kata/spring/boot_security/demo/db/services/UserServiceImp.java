package ru.kata.spring.boot_security.demo.db.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.dao.UserDao;
import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return (userDao.getAllUsers());
    }

    @Override
    public User getUserByID(long id) {
        return (userDao.getUserByID(id));
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void changeUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.changeUser(user);
    }
}
