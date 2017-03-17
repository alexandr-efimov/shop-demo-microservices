package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by alexandr.efimov on 3/3/2017.
 */
@RestController
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> all() {
        log.info("Get all users");
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(value = "/user/{id}")
    public User getUser(@PathVariable("id") long id, Principal principal) {
        log.warn("Principal: " + principal);
        log.info("Get user by id: " + id);
        return userRepository.findOne(id);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/user_secure", method = RequestMethod.GET)
    public List<User> allWithScopeServer(Principal principal) {
        log.warn("Get all users SECURE ZONE ---> server scope");
        log.warn("Principal: " + principal);
        return (List<User>) userRepository.findAll();
    }

}
