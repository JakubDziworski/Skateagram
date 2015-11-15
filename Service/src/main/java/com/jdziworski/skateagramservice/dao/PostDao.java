package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.domain.Post;
import com.jdziworski.skateagramservice.domain.User;

import java.util.Collections;
import java.util.List;

/**
 * Created by kuba on 13.10.2015.
 */
public interface PostDao {
    Post getPostForId(String id);
    List<Post> getPostForUser(String userId);
}
