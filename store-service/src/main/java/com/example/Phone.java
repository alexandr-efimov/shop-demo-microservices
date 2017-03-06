package com.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by alexandr.efimov on 3/3/2017.
 */
@Data
@Entity
@NoArgsConstructor
public class Phone implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String company;
    private String model;
    private double screenDiagonal;
    private double price;

    public Phone(String company, String model, double screenDiagonal, double price) {
        this.company = company;
        this.model = model;
        this.screenDiagonal = screenDiagonal;
        this.price = price;
    }
}
