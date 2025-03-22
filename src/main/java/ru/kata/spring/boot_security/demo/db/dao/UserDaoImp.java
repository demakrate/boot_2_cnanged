package ru.kata.spring.boot_security.demo.db.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager manager;

    private final RegistrationService registrationService;

    public UserDaoImp(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @Override
    public List<User> getAllUsers() {
        try {
            TypedQuery<User> query = manager.createQuery("from User", User.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return (new ArrayList<>());
        }
    }

    @Override
    public User getUserByID(long id) throws EntityNotFoundException {
        Query query = manager.createQuery("SELECT user FROM User user WHERE user.id = :id");
        query.setParameter("id", id);
        List users = query.getResultList();
        if (!users.isEmpty()) {
            return ((User) users.get(0));
        } else {

            throw new EntityNotFoundException("Пользователь с id: " + id + " не найден.");
        }
    }

    @Override
    public void addUser(User user) {

        registrationService.registerUser(user);
    }

    @Override
    public void deleteUser(long id) {
        getUserByID(id);
        Query query = manager.createQuery("DELETE User user WHERE id IN (:id)");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void changeUser(User user) {
        getUserByID(user.getId());
        manager.merge(user);
    }
}
