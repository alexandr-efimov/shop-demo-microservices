package com.example.client;

import com.example.model.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */

@FeignClient(value = "STORE-SERVICE", fallback = ProductClientFallbackImpl.class)
public interface ProductClientFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/phone/")
    public List<Product> findAll();

    @RequestMapping(method = RequestMethod.GET, value = "/phone/{id}")
    public Product getOne(long id);

}
