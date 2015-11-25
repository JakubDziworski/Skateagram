package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.dao.rowmappers.PostRowMapper;
import com.jdziworski.skateagramservice.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kuba on 13.10.2015.
 */
@Repository
public class PostDaoImpl extends BasicDaoImpl implements PostDao {

    @Override
    public Post getPostForId(String postId) {
        return jdbcTemplate.queryForObject("SELECT * FROM POSTS where idpost = ?", new PostRowMapper(), postId);
    }

    @Override
    public List<Post> getPostForUser(String userId) {
        return jdbcTemplate.query("SELECT * FROM POSTS where idpublisher = ?",new PostRowMapper(),userId);
    }

    @Override
    public List<Post> getFriendsPostsForUser(String userId) {
        return jdbcTemplate.query("SELECT * FROM POSTS where idpublisher = ?",new PostRowMapper(),userId);
    }
}
