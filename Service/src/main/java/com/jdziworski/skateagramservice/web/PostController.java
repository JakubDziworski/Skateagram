package com.jdziworski.skateagramservice.web;

import com.jdziworski.skateagramservice.domain.Post;
import com.jdziworski.skateagramservice.service.DropBoxService;
import com.jdziworski.skateagramservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by kuba on 15.11.2015.
 */
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    DropBoxService dropBoxService;

    @RequestMapping("/{postId}")
    HttpEntity<Post> getPostForId(@PathVariable String postId) {
        Post post = postService.getPostForId(postId);
        post.add(linkTo(methodOn(PostController.class).getPostForId(postId)).withSelfRel());
        return new HttpEntity<>(post);
    }

    @RequestMapping("/{userId}")
    HttpEntity<Post> getUserPosts(@PathVariable String postId) {
        Post post = postService.getPostForId(postId);
        post.add(linkTo(methodOn(PostController.class).getPostForId(postId)).withSelfRel());
        return new HttpEntity<>(post);
    }

    @RequestMapping("/{userId}")
    HttpEntity<Post> getFriendsPostsForUser(@PathVariable String postId) {
        Post post = postService.getPostForId(postId);
        post.add(linkTo(methodOn(PostController.class).getPostForId(postId)).withSelfRel());
        return new HttpEntity<>(post);
    }
}
