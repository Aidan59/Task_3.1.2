package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import spring_mvc.model.User;

public interface UserService {
    List<User> findAll();
    void addUser(User user);
    User getUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    boolean existsByEmail(String email, Long excludeId);
}
