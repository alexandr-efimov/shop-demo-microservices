package com.example.client;

import com.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
@Component
@Slf4j
public class UserClient implements RestServiceClient<User> {
    private static final String LINK_SUFFIX_USER = "/user/";
    private static final String SERVICE_ID_USER_SERVICE = "USER-SERVICE";
    private static final String ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE = "Can't get service instance: " + SERVICE_ID_USER_SERVICE;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public Collection<User> findAll() {
        Optional<String> urlToService = Optional.ofNullable(ClientUtils.getLinkToCLientInstanceFromLoadBalancer(loadBalancer, SERVICE_ID_USER_SERVICE));
        String urlToUsers = urlToService.orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE)) + LINK_SUFFIX_USER;
        ResponseEntity<List<User>> response = restTemplate.exchange(urlToUsers, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        log.info("Response from: " + urlToUsers + ", " + response);
        List<User> users = response.getBody();
        log.info("Got users: " + users);
        return users;
    }


    @Override
    public User getOne(long id) {
        Optional<String> urlToService = Optional.ofNullable(ClientUtils.getLinkToCLientInstanceFromLoadBalancer(loadBalancer, SERVICE_ID_USER_SERVICE));
        String urlToUserById = urlToService.orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE)) + LINK_SUFFIX_USER + id;

        ResponseEntity<User> response = restTemplate.exchange(urlToUserById, HttpMethod.GET, null, User.class);
        log.info("Response from: " + urlToUserById + ", " + response);
        return response.getBody();
    }
}
