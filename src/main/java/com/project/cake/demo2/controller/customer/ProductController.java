package com.project.cake.demo2.controller.customer;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepo) {
        this.productRepository = productRepo;
    }

    @GetMapping("/customer")
    private String viewHomePage(Model model, HttpSession session) {
        List<Product> list=productRepository.findAll();
        session.setAttribute("listProduct",list);

        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");
        int productCount = 0;
        for(Cart cart:listCart){
            productCount+=cart.getQuantity();
        }
        model.addAttribute("productCount", productCount);

        return "customer/home";
    }

    @GetMapping("/customer/product/{id}")
    private String viewDetail(@PathVariable("id") int id, Model model, HttpSession session) {
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product.orElse(null));

        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");
        int productCount = 0;
        for(Cart cart:listCart){
            productCount+=cart.getQuantity();
        }
        model.addAttribute("productCount", productCount);

        return "customer/product-detail";
    }

}
