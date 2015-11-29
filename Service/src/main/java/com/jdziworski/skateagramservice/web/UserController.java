package com.jdziworski.skateagramservice.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import com.jdziworski.skateagramservice.domain.User;
import com.jdziworski.skateagramservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba on 14.11.2015.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.POST)
    HttpEntity<User> createUser(@RequestBody @Valid User user) {
        userService.save(user);
        user.add(linkForUser(user));
        return new ResponseEntity<User>(user,HttpStatus.CREATED);
    }

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
        if(user == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        user.add(linkForUser(user));
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    private Link linkForUser(User user) {
        return linkTo(methodOn(UserController.class).getUserForId(user.getUsername())).withSelfRel();
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DuplicateKeyException.class)
    public void handleDuplicateKey() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(value={DataIntegrityViolationException.class,DataIntegrityViolationException.class})
    public HttpEntity<String> handleDataIntegrityError(DataIntegrityViolationException exception) {
        return new HttpEntity<String>("Wrong format. Login and password must be at least 3 characters long");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException( MethodArgumentNotValidException error ) {
        StringBuilder message = new StringBuilder("Błędy w formularzu:");
        if (error.getBindingResult().hasFieldErrors("username")) {
            message.append("\r\nBłędny format loginu - minimalnei (3-30 znaków)");
        }
        if (error.getBindingResult().hasFieldErrors("password")) {
            message.append("\r\nBłędny format hasła - minimalnei (3-30 znaków)");
        }
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
}
