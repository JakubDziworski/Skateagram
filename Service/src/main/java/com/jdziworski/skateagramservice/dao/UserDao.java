package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.domain.User;

import java.util.List;

/**
 * Created by kuba on 23.10.2015.
 */
public interface UserDao {
    List<User> getUsers();
    User getUserForId(String personId);
}
