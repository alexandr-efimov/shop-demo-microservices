package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
@Entity
@Data
@ToString(exclude = "currentOrder")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private double price;

    @ManyToOne
    @JoinColumn
    private Order currentOrder;
}
