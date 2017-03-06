package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class StoreServiceApplication {

    @Autowired
    private PhoneRepository phoneRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }

    @PostConstruct
    public void putTestData() {
        Phone samsung = new Phone("Samsung", "Galaxy 2", 5, 1500);
        Phone iphone = new Phone("Apple", "Iphone 7", 5, 2200);

        log.info("Save: " + samsung);
        log.info("Save: " + iphone);
        phoneRepository.save(samsung);
        phoneRepository.save(iphone);
    }
}
