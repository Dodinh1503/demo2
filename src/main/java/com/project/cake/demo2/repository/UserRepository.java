package com.project.cake.demo2.repository;

import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsernameAndPassword(String name, String pass);
}
