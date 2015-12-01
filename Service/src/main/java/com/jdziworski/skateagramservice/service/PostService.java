package com.jdziworski.skateagramservice.service;

import com.jdziworski.skateagramservice.domain.Post;

import java.util.List;

/**
 * Created by kuba on 15.11.2015.
 */
public interface PostService {
    Post getPostForId(String id);
    List<Post> getPostsForUser(String userId);
    List<Post> getFriendsPostsForUser(String userId);
    void save(Post post);
}
