package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.domain.Comment;
import com.jdziworski.skateagramservice.domain.Post;

import java.util.List;

/**
 * Created by kuba on 23.10.2015.
 */
public interface CommentDao {
    List<Comment> getCommentsForPost(Post post);
}
