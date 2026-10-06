package ru.kata.spring.boot_security.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUser(User user) {
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existing = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getId()));

        existing.setName(user.getName());
        existing.setSurname(user.getSurname());
        existing.setAge(user.getAge());
        existing.setEmail(user.getEmail());
        existing.setSalary(user.getSalary());
        existing.setPosition(user.getPosition());
        existing.setEmploymentType(user.getEmploymentType());
        existing.setRoles(user.getRoles());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email, long excludeId) {
        return userRepository.existsByEmailAndIdNot(email, excludeId);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> findRolesByIds(List<Long> ids) {
        return ids == null ? new HashSet<>() : new HashSet<>(roleRepository.findAllById(ids));
    }
}
