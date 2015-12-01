package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.dao.rowmappers.UserRowMapper;
import com.jdziworski.skateagramservice.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kuba on 14.11.2015.
 */
@Repository
public class UserDaoImpl extends BasicDaoImpl implements UserDao {
    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * from users", new UserRowMapper());
    }

    @Override
    public User getUserForId(String personId) {
        return jdbcTemplate.queryForObject("SELECT * from users where users.username = ?", new UserRowMapper(), personId);
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update("INSERT INTO users (username,password,enabled) VALUES(?,?,true)", user.getUsername(), user.getPassword());
        jdbcTemplate.update("INSERT INTO authorities(username,authority) VALUES(?,'ROLE_USER')", user.getUsername());
        return user;
    }

    @Override
    public void setUserFollowed(String follower, String followed) {
        jdbcTemplate.update("INSERT INTO friends VALUES(?,?)", follower,followed);
    }

    @Override
    public List<User> findNotFollowed(String userId) {
        return jdbcTemplate.query("SELECT * FROM users " +
                "where NOT(username = ANY(select followed from friends where follower = ?)) " +
                "AND (username != ?); ", new UserRowMapper(),userId,userId);
    }
}
