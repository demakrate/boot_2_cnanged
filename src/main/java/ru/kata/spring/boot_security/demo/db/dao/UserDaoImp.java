package ru.kata.spring.boot_security.demo.db.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager manager;

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
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Пользователь с id: " + id + " не найден.");
        }

    }

    @Override
    public void addUser(User user) {
        if (!findByEmail(user.getMail()).isEmpty()) {
            throw new DataIntegrityViolationException("Пользователь с таким email уже существует");
        }
        manager.persist(user);
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

    @Override
    public User findByUsernameWithRoles(String mail) {
        try {
            return manager.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.mail = :mail", User.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        try {
            User user = manager.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.mail = :mail", User.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
