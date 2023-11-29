package com.project.cake.demo2.repository;

import com.project.cake.demo2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
