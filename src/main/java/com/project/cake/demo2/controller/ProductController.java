package com.project.cake.demo2.controller;

import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepo) {
        this.productRepository = productRepo;
    }

    @GetMapping("/")
    public String viewHomePage(Model model, HttpSession session) {
        List<Product>list=productRepository.findAll();
        session.setAttribute("listProduct",list);
        for(Product i: list)
            System.out.println(i.getLinkImg());
        return "home";
    }

    @GetMapping("/product/{id}")
    public String viewDetail(@PathVariable("id") int id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product.orElse(null));
        return "product-detail";
    }
}
