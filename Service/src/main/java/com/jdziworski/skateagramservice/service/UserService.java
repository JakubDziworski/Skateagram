package com.jdziworski.skateagramservice.service;

import com.jdziworski.skateagramservice.dao.PostDao;
import com.jdziworski.skateagramservice.domain.Post;
import com.jdziworski.skateagramservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import java.util.List;

/**
 * Created by kuba on 13.10.2015.
 */
public interface UserService {
    List<User> getUsers();
    User getUserForId(String personId);
}
