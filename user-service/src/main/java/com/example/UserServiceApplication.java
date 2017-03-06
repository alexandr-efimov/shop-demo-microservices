package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class UserServiceApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @PostConstruct
    public void putTestDataToDB() {

        List<User> users = Arrays.asList(new User(null, "user1", "user1@mail.ru"),
                new User(null, "user2", "user2@yandex.ru"),
                new User(null, "user3", "user3@gmail.ru"));
        log.info("Save users to db: " + users);

        userRepository.save(users);
    }
}
