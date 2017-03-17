package com.shop.auth.controller;

import com.shop.auth.domain.User;
import com.shop.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Test controller for storing some information and testing access to resources.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }


    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@Valid @RequestBody User user) {
        userService.create(user);
    }

    @RequestMapping(value = "/testHello", method = RequestMethod.GET)
    public String test() {
        return "WELCOMEEEEEEEEEEE";
    }

    @RequestMapping(value = "/teat_get_users", method = RequestMethod.GET)
    public List<User> testGetUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "/teat_get_one", method = RequestMethod.GET)
    public User testGetOne() {
        User user = new User();
        user.setUsername("Name");
        user.setPassword("Password");
        return user;
    }

}
