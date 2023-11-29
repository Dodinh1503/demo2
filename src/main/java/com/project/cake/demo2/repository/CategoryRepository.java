package com.project.cake.demo2.repository;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Category;
import com.project.cake.demo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
