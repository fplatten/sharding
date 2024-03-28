package com.culture.sharding.orders.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orgoid;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<CustomerOrder> orders;

    // Constructors, Getters and Setters
}
