package com.jdziworski.skateagramservice.web;

import com.jdziworski.skateagramservice.domain.Post;
import com.jdziworski.skateagramservice.service.DropBoxService;
import com.jdziworski.skateagramservice.service.PostService;
import com.jdziworski.skateagramservice.service.ZencoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by kuba on 15.11.2015.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    ZencoderService zencoderService;


    @RequestMapping(method = RequestMethod.POST)
    HttpEntity<Post> savePost(@RequestBody Post post,Principal principal) {
        post.setUserId(principal.getName());
        post.setDate(Calendar.getInstance().getTime());
        final String encodedVideoURL = zencoderService.encode(post.getVideoId());
        post.setVideoId(encodedVideoURL);
        postService.save(post);
        return new HttpEntity<>(post);
    }

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

    @RequestMapping("/wall")
    HttpEntity<List<Post>> getFriendsPostsForUser(Principal principal) {
        return new HttpEntity<>(postService.getFriendsPostsForUser(principal.getName()));
    }
}
