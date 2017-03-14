package com.example.client;

import com.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

/**
 * Fallback implementation for requests.
 */
@Component
@Slf4j
public class UserClientFallbackImpl implements UserClientFeign {

    @Override
    public List<User> findAll() {
        log.error("No user service, fallback method getAll");
        User stubUser = new User(null, "stub@mail", null);
        return Arrays.asList(stubUser);
    }

    @Override
    public User getOne(@PathVariable("id") long id) {
        log.error("No user service, fallback method getOne");
        return new User(null, "stub@mail", null);
    }
}