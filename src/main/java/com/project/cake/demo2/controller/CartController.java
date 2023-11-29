package com.project.cake.demo2.controller;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import com.project.cake.demo2.repository.CartRepository;
import com.project.cake.demo2.repository.ProductRepository;
import com.project.cake.demo2.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final UserRepository userRepository;

    public CartController(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/cart/{id}")
    public String addToCart(@PathVariable("id") int id, HttpSession session){
        List<Product>list= (List<Product>) session.getAttribute("listProduct");
        User user = (User) session.getAttribute("user");

        Product p=new Product();
        for(Product i:list)
            if(i.getId()==id)
            {
                p=i;
                break;
            }
        Cart cart=new Cart();
        cart.setProduct(p);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setTotal(p.getPrice());
        cart.setUser(user);
        cartRepository.save(cart);

        List<Cart>listCart=cartRepository.findAll();
        session.setAttribute("listCart",listCart);
        for(Cart i:listCart){
            System.out.println(i.getProduct().getId());
            System.out.println(i.getProduct().getName());
        }
        List<Product>listProducts= (List<Product>) session.getAttribute("listProduct");
        session.setAttribute("listProduct",listProducts);
        return "cart";
    }

}
