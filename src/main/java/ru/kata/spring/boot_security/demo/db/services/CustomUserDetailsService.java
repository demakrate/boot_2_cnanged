package ru.kata.spring.boot_security.demo.db.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.dao.UserDao;
import ru.kata.spring.boot_security.demo.db.models.User;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }
}
