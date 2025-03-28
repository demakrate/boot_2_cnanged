package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kata.spring.boot_security.demo.converters.StringToRoleConverter;
import ru.kata.spring.boot_security.demo.db.services.RoleService;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final RoleService roleService;

    public MvcConfig(RoleService roleService) {
        this.roleService = roleService;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/admin").setViewName("admin");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToRoleConverter(roleService));
    }
}
