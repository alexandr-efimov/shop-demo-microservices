package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by alexandr.efimov on 3/6/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
