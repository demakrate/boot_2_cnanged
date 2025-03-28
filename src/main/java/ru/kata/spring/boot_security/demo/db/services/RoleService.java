package ru.kata.spring.boot_security.demo.db.services;

import ru.kata.spring.boot_security.demo.db.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void save(Role role);

    Role findByName(String roleName);
}
