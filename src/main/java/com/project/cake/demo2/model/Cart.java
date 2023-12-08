package com.project.cake.demo2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private float total;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_Id", referencedColumnName = "id")
    private Product product;
}