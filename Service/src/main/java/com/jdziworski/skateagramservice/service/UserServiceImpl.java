package com.jdziworski.skateagramservice.service;

import com.jdziworski.skateagramservice.dao.UserDao;
import com.jdziworski.skateagramservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kuba on 13.10.2015.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUserForId(String personId) {
        return userDao.getUserForId(personId);
    }
}
