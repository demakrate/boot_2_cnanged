package ru.kata.spring.boot_security.demo.db.dao;

import ru.kata.spring.boot_security.demo.db.models.Role;

import java.util.List;

public interface RoleDao {
    Role findByName(String role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void save(Role role);
}
