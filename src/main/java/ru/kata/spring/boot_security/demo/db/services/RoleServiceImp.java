package ru.kata.spring.boot_security.demo.db.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.dao.RoleDao;
import ru.kata.spring.boot_security.demo.db.models.Role;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return (roleDao.getAllRoles());
    }

    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }
}
