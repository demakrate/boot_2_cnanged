package ru.kata.spring.boot_security.demo.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.services.RoleService;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    private final RoleService roleService;

    public StringToRoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String roleName) {
        return roleService.findByName(roleName);
    }

}
