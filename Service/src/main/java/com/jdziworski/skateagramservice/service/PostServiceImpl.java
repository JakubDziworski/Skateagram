package com.jdziworski.skateagramservice.service;

import com.jdziworski.skateagramservice.dao.PostDao;
import com.jdziworski.skateagramservice.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kuba on 15.11.2015.
 */
@Service
public class PostServiceImpl implements PostService {
   @Autowired
    private PostDao postDao;

    @Override
    public Post getPostForId(String id) {
        return postDao.getPostForId(id);
    }

    @Override
    public List<Post> getPostsForUser(String userId) {
        return postDao.getPostForUser(userId);
    }

    @Override
    public List<Post> getFriendsPostsForUser(String userId) {
        return postDao.getFriendsPostsForUser(userId);
    }

    @Override
    public void save(Post post) {
         postDao.save(post);
    }
}
