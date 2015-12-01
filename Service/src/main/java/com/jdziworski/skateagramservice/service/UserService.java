package com.jdziworski.skateagramservice.service;

import com.jdziworski.skateagramservice.dao.PostDao;
import com.jdziworski.skateagramservice.domain.Post;
import com.jdziworski.skateagramservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by kuba on 13.10.2015.
 */
public interface UserService {
    User save(User user);
    List<User> getUsers();
    User getUserForId(String personId);
    void setUserFollowed(String follower, String followed);
    List<User> getNotFollowedUsers(String name);
}
