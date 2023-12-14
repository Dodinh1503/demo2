package com.project.cake.demo2.repository;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUserAndProduct(User user, Product p);

    List<Cart> findByUser(User user);
}
