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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int status;

    @ManyToOne(targetEntity = CategoryParent.class)
    @JoinColumn(name = "parent_Id", referencedColumnName = "id")
    @JsonIgnore
    private CategoryParent categoryParent;
}
