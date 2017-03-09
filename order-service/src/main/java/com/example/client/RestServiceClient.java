package com.example.client;

/**
 * Created by alexandr.efimov on 3/9/2017.
 */

import java.util.List;

/**
 * Rest client to some service for entities of {@link T} type
 *
 * @param <T> is type of entity
 */
public interface RestServiceClient<T> {

    /**
     * Get all entities
     *
     * @return collection with entities
     * @throws IllegalStateException if can't connect to service instance
     */
    List<T> findAll();

    /**
     * Get one entity by id
     *
     * @param id is id for finding entity
     * @return entity by id
     * @throws IllegalStateException if can't connect to service instance
     */
    T getOne(long id);
}
