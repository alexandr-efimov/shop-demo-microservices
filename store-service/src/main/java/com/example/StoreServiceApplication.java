package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        Phone lg = new Phone("Lg", "G5", 5.5, 408);
        Phone alcatel = new Phone("Alcatel", "IDOL 4s", 4, 288);


        List<Phone> list = new ArrayList<>();
        Collections.addAll(list, samsung, iphone, lg, alcatel);
        phoneRepository.save(list);
    }
}
