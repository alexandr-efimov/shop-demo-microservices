package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
@Entity
@Data
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "currentOrder", cascade = CascadeType.ALL)
    private List<Product> orderProducts = new ArrayList<>();
    private LocalDateTime orderDateTime;


    public double getTotalPrice() {
        return orderProducts.stream().map(Product::getPrice).reduce(Double::sum).orElseThrow(IllegalStateException::new);
    }
}
