package com.jdziworski.skateagramservice.dao.rowmappers;

import com.jdziworski.skateagramservice.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kuba on 13.10.2015.
 */
public class SpotRowMapper implements RowMapper<Spot> {

    @Override
    public Spot mapRow(ResultSet rs, int rowNum) throws SQLException {
        Spot spot = new Spot();
        spot.setName(rs.getString("name"));
        return spot;
    }
}
