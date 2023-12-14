package com.project.cake.demo2.repository;

import com.project.cake.demo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByNameContaining(String keyword);

}
