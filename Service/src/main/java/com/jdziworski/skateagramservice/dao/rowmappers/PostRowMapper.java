package com.jdziworski.skateagramservice.dao.rowmappers;

import com.jdziworski.skateagramservice.domain.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by kuba on 13.10.2015.
 */
public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setDate(rs.getDate("date"));
        post.setUserId(rs.getString("idpublisher"));
        post.setVideoId(rs.getString("idvideo"));
        post.setSpotId(rs.getString("idspot"));
        post.setTrickId(rs.getString("idtrick"));
        return post;
    }
}
