package com.jdziworski.skateagramservice.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import com.jdziworski.skateagramservice.domain.User;
import com.jdziworski.skateagramservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kuba on 14.11.2015.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        userService.getUsers().forEach(user -> {
            user.add(linkForUser(user));
            users.add(user);
        });
        return new HttpEntity(users);
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    HttpEntity<User> getUserForId(@PathVariable String personId) {
        User user = userService.getUserForId(personId);
        user.add(linkForUser(user));
        return new HttpEntity(user);
    }

    private Link linkForUser(User user) {
        return linkTo(methodOn(UserController.class).getUserForId(user.getNick())).withSelfRel();
    }
}
