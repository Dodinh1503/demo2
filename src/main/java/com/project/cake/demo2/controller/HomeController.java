package com.project.cake.demo2.controller;

import com.project.cake.demo2.model.Cart;
import com.project.cake.demo2.model.Category;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.model.User;
import com.project.cake.demo2.repository.CartRepository;
import com.project.cake.demo2.repository.CategoryRepository;
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

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartRepository cartRepository;
    @GetMapping("/")
    public String viewHome(HttpSession session) {
        List<Product> list = productRepository.findAll();
        session.setAttribute("listProduct", list);
        return "index";
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }

    @GetMapping("/product/{id}")
    public String viewDetail(@PathVariable("id") int id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product.orElse(null));
        return "product-detail";
    }

    @PostMapping(value = "login")
    public String loginPage(@RequestParam("name") String name, @RequestParam("pass") String pass, Model model, HttpSession session) {
        List<Product> list = productRepository.findAll();
        session.setAttribute("listProduct", list);
        List<Category> listCategory = categoryRepository.findAll();
        session.setAttribute("listCategory", listCategory);

        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);

        User isAdmin = userRepository.findByUsernameAndPassword(name, pass);
        if (isAdmin == null) {
            model.addAttribute("result", "false");
            return "login";
        }
        session.setAttribute("user", isAdmin);

        User user1 = (User) session.getAttribute("user");
        List<Cart> listCart = cartRepository.findByUser(user1);
        session.setAttribute("listCartByUser",listCart);
        int productCount = 0;
        for(Cart cart:listCart){
            productCount+=cart.getQuantity();
        }
        model.addAttribute("productCount", productCount);

        if (isAdmin.getIsAdmin() == 0)
            return "customer/home";
        return "admin/home";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user",null);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name, @RequestParam("pass") String pass,
                               @RequestParam("fullName") String fullName,@RequestParam("email") String email,
                               Model model) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        user.setName(fullName);
        user.setEmail(email);
        // Kiểm tra nếu người dùng đã tồn tại
        if (userRepository.findByUsername(user.getUsername()) != null) {
            // Xử lý logic khi tên người dùng đã tồn tại
            model.addAttribute("error", "Tên người dùng đã đăng ký. Vui lòng điền tên khác.");
            // Ví dụ: Hiển thị thông báo lỗi, chuyển hướng đến trang đăng ký lại
            return "register";
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email đã đăng ký. Vui lòng điền email khác.");
            return "register";
        }
        // Xử lý lưu thông tin người dùng mới
        userRepository.save(user);

        return "redirect:/login";
    }
    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Product> searchResult = productRepository.findByNameContaining(keyword);
        model.addAttribute("searchResult", searchResult);
        int cnt = searchResult.size();
        model.addAttribute("list",cnt);

        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");
        int productCount = 0;
        for(Cart cart:listCart){
            productCount+=cart.getQuantity();
        }
        model.addAttribute("productCount", productCount);
        if (user.getIsAdmin() == 0)
            return "customer/search-result";
        return "admin/search-result";
    }
    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable int id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.get();
        // Lấy danh sách sản phẩm thuộc danh mục này
        List<Product> productsInCategory = category.getProducts();
        model.addAttribute("productsInCategory", productsInCategory);
        model.addAttribute("category", category);

        int productCount = 0;
        List<Cart> listCart = (List<Cart>) session.getAttribute("listCartByUser");

        for (Cart i : listCart) {
            productCount+=i.getQuantity();
        }
        model.addAttribute("productCount", productCount);
        if (user.getIsAdmin() == 0)
            return "customer/category";
        return "admin/category";
    }
}
