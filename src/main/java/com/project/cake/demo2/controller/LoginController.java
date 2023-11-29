package com.project.cake.demo2.controller;

import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import com.project.cake.demo2.repository.ProductRepository;
import com.project.cake.demo2.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository proRepo;
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
    @PostMapping(value = "mainshop")
    public String loginPage(@RequestParam("name")String name, @RequestParam("pass")String pass, Model model, HttpSession session){
        List<Product> list=proRepo.findAll();
        session.setAttribute("listProduct",list);
        User user=new User();
        user.setUsername(name);
        user.setPassword(pass);
        User response=userRepo.findByUsernameAndPassword(name, pass);
        if(response==null)
        {
            model.addAttribute("result","false");
            return "login";
        }
        session.setAttribute("user",response);
        return "mainshop";
    }

}
