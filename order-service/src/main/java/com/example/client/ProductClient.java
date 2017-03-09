package com.example.client;

import com.example.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */

@Component
@Slf4j
public class ProductClient implements RestServiceClient<Product> {
    public static final String LINK_SUFFIX_PHONE = "/phone/";
    private static final String SERVICE_ID_STORE_SERVICE = "STORE-SERVICE";
    private static final String ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE = "Can't get service instance: " + SERVICE_ID_STORE_SERVICE;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public List<Product> findAll() {
        Optional<String> urlToService = Optional.ofNullable(ClientUtils.getLinkToCLientInstanceFromLoadBalancer(loadBalancer, SERVICE_ID_STORE_SERVICE));
        String urlToPhones = urlToService.orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE)) + LINK_SUFFIX_PHONE;

        ResponseEntity<List<Product>> response = restTemplate.exchange(urlToPhones, HttpMethod.GET
                , null, new ParameterizedTypeReference<List<Product>>() {
                });

        log.info("Response from: " + urlToPhones + ", " + response);
        List<Product> products = response.getBody();
        log.info("Got products: " + products);
        return products;
    }

    @Override
    public Product getOne(long id) {
        Optional<String> urlToService = Optional.ofNullable(ClientUtils.getLinkToCLientInstanceFromLoadBalancer(loadBalancer, SERVICE_ID_STORE_SERVICE));
        String urlToPhones = urlToService.orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE_NO_CONNECTION_TO_SERVICE)) + LINK_SUFFIX_PHONE + "/" + id;

        log.info("Link to phone: " + urlToPhones);
        ResponseEntity<Product> response = restTemplate.exchange(urlToPhones, HttpMethod.GET, null, Product.class);
        log.info("Response from: " + urlToPhones + ", " + response);
        return response.getBody();
    }

}
