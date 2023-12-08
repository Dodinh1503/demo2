package com.project.cake.demo2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, linkImg, description;
    private float price, discount;
    private int quantity, amountSold, status,amountLeft;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name="category_Id", referencedColumnName = "id")
    @JsonIgnore
    private Category category;
}