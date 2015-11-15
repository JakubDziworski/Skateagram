package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.dao.rowmappers.UserRowMapper;
import com.jdziworski.skateagramservice.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kuba on 14.11.2015.
 */
@Repository
public class UserDaoImpl extends BasicDaoImpl implements UserDao {
    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * from users",new UserRowMapper());
    }

    @Override
    public User getUserForId(String personId) {
        return jdbcTemplate.queryForObject("SELECT * from users where users.nick = ?", new UserRowMapper(), personId);
    }
}
