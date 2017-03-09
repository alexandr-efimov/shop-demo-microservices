package com.example.client;

import com.example.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by alexandr.efimov on 3/9/2017.
 */
@FeignClient("USER-SERVICE")
public interface UserClientFeign {


    @RequestMapping(method = RequestMethod.GET, value = "/user/")
    List<User> findAll();

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    User getOne(@PathVariable("id") long id);
}
