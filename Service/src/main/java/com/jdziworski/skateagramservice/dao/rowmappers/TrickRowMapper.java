package com.jdziworski.skateagramservice.dao.rowmappers;

import com.jdziworski.skateagramservice.domain.Spot;
import com.jdziworski.skateagramservice.domain.Trick;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kuba on 13.10.2015.
 */
public class TrickRowMapper implements RowMapper<Trick> {

    @Override
    public Trick mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trick trick = new Trick();
        trick.setUrl(rs.getString("url"));
        return trick;
    }
}
