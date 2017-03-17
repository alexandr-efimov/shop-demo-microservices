package com.shop.auth.service;

import com.shop.auth.domain.User;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> getAll();


}
