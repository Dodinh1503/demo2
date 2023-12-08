package com.project.cake.demo2.controller.customer;

import com.project.cake.demo2.repository.CartRepository;
import com.project.cake.demo2.repository.ProductRepository;
import com.project.cake.demo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;

    public CheckoutController(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/checkout")
    public String viewCheckout() {
        return "customer/checkout";
    }
}
