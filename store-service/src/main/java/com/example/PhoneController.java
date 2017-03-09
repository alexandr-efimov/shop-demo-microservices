package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alexandr.efimov on 3/3/2017.
 */
@RestController
@Slf4j
public class PhoneController {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneController(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @GetMapping("/phone/")
    public ResponseEntity<List<Phone>> all() {
        log.info("Get all phones");
        return new ResponseEntity<>((List<Phone>) phoneRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/phone/{id}")
    public ResponseEntity<Phone> getPhone(@PathVariable("id") long id) {
        log.info("Get phone by id: " + id);
        return new ResponseEntity<>(phoneRepository.findOne(id), HttpStatus.OK);
    }
}
