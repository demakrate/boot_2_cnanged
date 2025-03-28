package ru.kata.spring.boot_security.demo.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.db.models.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Role findByName(String role){
        Query query = manager.createQuery("SELECT role FROM Role role WHERE role.roleName = :roleName");
        query.setParameter("roleName", role);
        return (Role) query.getSingleResult();
    }


    @Override
    public List<Role> getAllRoles() {
        try {
            TypedQuery<Role> query = manager.createQuery("from Role", Role.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return (new ArrayList<>());
        }
    }

    @Override
    public Role getRoleById(Long id) {
        Query query = manager.createQuery("SELECT role FROM Role role WHERE role.id = :id");
        query.setParameter("id", 1);
        return (Role) query.getSingleResult();
    }

    @Override
    public void save(Role role) {
        manager.persist(role);
    }


}
