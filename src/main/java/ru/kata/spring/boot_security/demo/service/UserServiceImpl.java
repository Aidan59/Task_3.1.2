package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_mvc.dao.UserDao;
import spring_mvc.model.User;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email, Long excludeId) {
        return userDao.existsByEmail(email, excludeId);
    }
}
