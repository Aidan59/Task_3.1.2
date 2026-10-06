package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    List<User> findAll();
    void addUser(User user);
    User getUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email, long excludeId);
    List<Role> findAllRoles();
    Set<Role> findRolesByIds(List<Long> ids);

}
