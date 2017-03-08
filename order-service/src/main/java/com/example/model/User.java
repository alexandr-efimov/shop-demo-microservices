package com.example.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */

@Entity
@Data
@ToString(exclude = "orders")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
