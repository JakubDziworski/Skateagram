package com.jdziworski.skateagramservice.web;

import com.jdziworski.skateagramservice.domain.Post;
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

    @RequestMapping("/{postId}")
    HttpEntity<Post> getPostForId(@PathVariable String postId) {
        Post post = postService.getPostForId(postId);
        post.add(linkTo(methodOn(PostController.class).getPostForId(postId)).withSelfRel());
        return new HttpEntity<>(post);
    }
}
