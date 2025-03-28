package ru.kata.spring.boot_security.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.services.RoleService;
import ru.kata.spring.boot_security.demo.db.services.UserService;


import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {


    private final UserService userService;


    private final RoleService roleService;


    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) {
        Role userRole = new Role();
        userRole.setName("USER");
        roleService.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleService.save(adminRole);


        User user = new User();
        user.setName("user");
        user.setAge(1);
        user.setPassword("password");
        user.setMail("1@mail.ru");
        user.setRoles(Set.of(userRole));
        userService.addUser(user);


        User admin = new User();
        admin.setName("admin");
        admin.setAge(2);
        admin.setPassword("password");
        admin.setMail("2@mail.ru");
        admin.setRoles(Set.of(adminRole, userRole));
        userService.addUser(admin);
    }
}