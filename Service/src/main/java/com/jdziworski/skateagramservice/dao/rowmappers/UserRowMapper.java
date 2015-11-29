package com.jdziworski.skateagramservice.dao.rowmappers;

import com.jdziworski.skateagramservice.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kuba on 13.10.2015.
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    }
}
