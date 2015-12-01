package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by kuba on 23.10.2015.
 */
public interface UserDao {
    List<User> getUsers();
    User getUserForId(String personId);
    User save(User user);
    void setUserFollowed(String follower, String followed);
    List<User> findNotFollowed(String userId);
}
