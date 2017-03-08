package com.example.client;

import com.example.model.Product;
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
public class ProductClient {
    private static final String SERVICE_NAME_STORE_SERVICE = "STORE-SERVICE";
    private String urlToPhones;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private LoadBalancerClient loadBalancer;

    //TODO fix dependency to other services - can't start up service if no user,order services in Eureka
    @PostConstruct
    private void initServiceInstance() {
        ServiceInstance serviceInstance = loadBalancer.choose(SERVICE_NAME_STORE_SERVICE);
        log.info("Service instance: " + SERVICE_NAME_STORE_SERVICE + "from load balancer service instance: " + serviceInstance);
        urlToPhones = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/phone";
        log.info("Url to phones: " + urlToPhones);
    }

    public Collection<Product> findAll() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(urlToPhones, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        });

        log.info("Response from: " + urlToPhones + ", " + response);
        List<Product> products = response.getBody();
        log.info("Got products: " + products);
        return products;
    }


    public Product getOne(long id) {
        String urlToPhoneById = urlToPhones + "/" + id;
        ResponseEntity<Product> response = restTemplate.exchange(urlToPhoneById, HttpMethod.GET, null, Product.class);

        log.info("Response from: " + urlToPhoneById + ", " + response);
        return response.getBody();
    }

}
