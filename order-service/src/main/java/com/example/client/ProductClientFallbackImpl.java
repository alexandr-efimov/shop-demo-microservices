package com.example.client;

/**
 * Created by alexandr.efimov on 3/14/2017.
 */

import com.example.model.Product;
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
public class ProductClientFallbackImpl implements ProductClientFeign {

    @Override
    public List<Product> findAll() {
        log.error("No store service, fallback method getAll");
        return Arrays.asList(new Product(null, "stub", 0, null));
    }

    @Override
    public Product getOne(@PathVariable("id") long id) {
        log.error("No store service, fallback method getOne");
        return new Product(null, "stub", 0, null);
    }
}