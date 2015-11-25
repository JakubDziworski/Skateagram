package com.jdziworski.skateagramservice.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import com.jdziworski.skateagramservice.domain.User;
import com.jdziworski.skateagramservice.utils.UserUtils;
import com.sun.deploy.net.HttpResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * Created by kuba on 12.10.2015.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping
    HttpEntity<ResourceSupport> home() {
        ResourceSupport response = new ResourceSupport();
        Link self = linkTo(methodOn(MainController.class).home()).withSelfRel();
        Link users = linkTo(methodOn(UserController.class).getUsers()).withRel("users");
        response.add(self);
        response.add(users);
        return new HttpEntity(response);
    }
}
