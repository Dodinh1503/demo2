package com.project.cake.demo2.controller.customer;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import com.project.cake.demo2.repository.CartRepository;
import com.project.cake.demo2.repository.ProductRepository;
import com.project.cake.demo2.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private  UserRepository userRepository;


    @PostMapping("/cart/{idProduct}")
    public String addToCart(@PathVariable("idProduct") int id,@RequestParam("quantity") int quantity, HttpSession session, Model model) {

        List<Product> list = (List<Product>) session.getAttribute("listProduct");
        User user = (User) session.getAttribute("user");
        Product p = new Product();
        for (Product i : list)
            if (i.getId() == id) {
                p = i;
                break;
            }
        Cart existingCart = cartRepository.findByUserAndProduct(user, p);
        if (existingCart != null) {
            existingCart.setProduct(existingCart.getProduct());
            existingCart.setUser(user);
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            existingCart.setTotal(existingCart.getTotal() + p.getPrice()*quantity);
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart();
            cart.setProduct(p);
            cart.setUser(user);
            cart.setQuantity(quantity);
            cart.setTotal(p.getPrice()*quantity);
            cartRepository.save(cart);

        }

        float totalPrice = 0;
        int productCount = 0;
        //lay list cart moi va luu cart vao session
        List<Cart> listCart = cartRepository.findByUser(user);
        session.setAttribute("listCartByUser", listCart);

        for (Cart i : listCart) {
            totalPrice += i.getTotal();
            productCount+=i.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("productCount", productCount);

        return "customer/cart";
    }

    @GetMapping("/delete/cart/{idCart}")
    public String delete(@PathVariable("idCart") int id, HttpSession session, Model model) {
        float totalPrice = 0;
        int productCount = 0;
        //lay list cart roi xoa theo id, sau do cap nhat lai session
        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");
        for (Cart i : listCart) {
            if (i.getId() == id) {
                cartRepository.deleteById(id);
                listCart.remove(i);
                break;
            }
        }
        session.setAttribute("listCart", listCart);

        for (Cart i : listCart) {
            totalPrice += i.getTotal();
            productCount+=i.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("productCount", productCount);

        return "customer/cart";
    }

    @GetMapping("/customer/cart")
    public String moveToCart(HttpSession session, Model model) {
        float totalPrice = 0;
        int productCount = 0;
        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");

        for (Cart i : listCart) {
            totalPrice += i.getTotal();
            productCount+=i.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("productCount", productCount);

        return "customer/cart";
    }

    @PostMapping("/update/cart")
    public String updateCart(@RequestParam("id") int[] cartIds, @RequestParam("quantity") int[] quantity, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        for (int idCart=0;idCart < cartIds.length; idCart++) {
            Cart cart = cartRepository.findById(cartIds[idCart]).orElse(null);
            System.out.println(cartIds[idCart]);
            if (cart != null) {
                cart.setProduct(cart.getProduct());
                cart.setUser(user);
                // Cập nhật số lượng sản phẩm trong giỏ hàng
                cart.setQuantity(quantity[idCart]);

                // Tính toán lại tổng tiền dựa trên số lượng mới và giá của sản phẩm
                float total = cart.getProduct().getPrice() * quantity[idCart];
                cart.setTotal(total);

                // Lưu thông tin giỏ hàng đã được cập nhật vào cơ sở dữ liệu
                cartRepository.save(cart);
            }
        }
        float totalPrice = 0;
        int productCount = 0;
        //lay list cart moi va luu cart vao session
        List<Cart> listCart = cartRepository.findByUser(user);
        session.setAttribute("listCartByUser", listCart);

        for (Cart i : listCart) {
            totalPrice += i.getTotal();
            productCount+=i.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("productCount", productCount);

        return "customer/cart";
    }

}