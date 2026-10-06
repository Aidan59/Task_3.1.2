package ru.kata.spring.boot_security.demo;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.enums.EmploymentType;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    public void init() {
        Role userRole = new Role();
        Role adminRole = new Role();

        userRole.setName("ROLE_USER");
        adminRole.setName("ROLE_ADMIN");

        roleService.save(userRole);
        roleService.save(adminRole);

        User user = new User();
        user.setName("User");
        user.setSurname("User");
        user.setAge(20);
        user.setEmail("user@mail.com");
        user.setPassword("user");
        user.setSalary(new BigDecimal(1000));
        user.setPosition("User");
        user.setEmploymentType(EmploymentType.FULL_TIME);
        user.setRoles(Set.of(userRole));

        User admin = new User();
        admin.setName("Admin");
        admin.setSurname("Admin");
        admin.setAge(30);
        admin.setEmail("admin@mail.com");
        admin.setPassword("admin");
        admin.setSalary(new BigDecimal(1000));
        admin.setPosition("Admin");
        admin.setEmploymentType(EmploymentType.PART_TIME);
        admin.setRoles(Set.of(userRole, adminRole));

        userService.addUser(user);
        userService.addUser(admin);
    }

}
