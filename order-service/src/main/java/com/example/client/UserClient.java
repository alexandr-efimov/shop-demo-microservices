package com.example.client;

import com.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
@Component
@Slf4j
public class UserClient {
    private static final String SERVICE_NAME_USER_SERVICE = "USER-SERVICE";
    private String urlToUsers;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void initServiceInstance() {
        ServiceInstance serviceInstance = loadBalancer.choose(SERVICE_NAME_USER_SERVICE);
        log.info("Service instance: " + SERVICE_NAME_USER_SERVICE + "from load balancer service instance: " + serviceInstance);
        urlToUsers = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user";
        log.info("Url to users: " + urlToUsers);
    }

    public Collection<User> findAll() {
        ResponseEntity<List<User>> response = restTemplate.exchange(urlToUsers, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        log.info("Response from: " + urlToUsers + ", " + response);
        List<User> users = response.getBody();
        log.info("Got users: " + users);
        return users;
    }


    public User getOne(long id) {
        String urlToUserById = urlToUsers + "/" + id;
        ResponseEntity<User> response = restTemplate.exchange(urlToUserById, HttpMethod.GET, null, User.class);

        log.info("Response from: " + urlToUserById + ", " + response);
        return response.getBody();
    }
}
