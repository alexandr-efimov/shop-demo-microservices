package com.example.repository;

import com.example.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
