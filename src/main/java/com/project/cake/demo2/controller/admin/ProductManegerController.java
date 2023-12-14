package com.project.cake.demo2.controller.admin;

import com.project.cake.demo2.model.Category;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.repository.CategoryRepository;
import com.project.cake.demo2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductManegerController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired

    @GetMapping("/admin")
    private String viewHomePage() {
        return "admin/home";
    }

    @GetMapping("/update/product")
    private String viewUpdateProduct(HttpSession session) {
        List<Product> list = productRepository.findAll();
        session.setAttribute("listProduct", list);
        List<Category> listCategory = categoryRepository.findAll();
        session.setAttribute("listCategory", listCategory);
        return "admin/home-update-product";
    }

    @GetMapping("/update/product/{id}")
    private String showUpdateProduct(@PathVariable("id") int idProduct, Model model) {
        Product product = productRepository.findById(idProduct).orElse(null);
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping("/update/product/{id}")
    private String updateProduct(@PathVariable("id") int idProduct, @RequestParam("name") String name,
                                 @RequestParam("description") String description, @RequestParam("price") String price,
                                 @RequestParam("discount") String discount,
                                 @RequestParam("quantity") int quantity, Model model) {
        Product product = productRepository.findById(idProduct).orElse(null);
        model.addAttribute("product", product);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(Float.parseFloat(price));
        product.setDiscount(Float.parseFloat(discount));
        product.setQuantity(quantity);
        try {
            productRepository.save(product);
            model.addAttribute("success", "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Cập nhật sản phẩm thất bại!");
        }

        return "admin/update-product";
    }

    @GetMapping("/delete/product/{idProduct}")
    private String deleteProduct(@PathVariable("idProduct") int idProduct, Model model, HttpSession session) {
        try {
            productRepository.deleteById(idProduct);
            model.addAttribute("successDelete", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorDelete", "Xóa sản phẩm thất bại!");
            return "admin/update-product";
        }
        List<Product> list = productRepository.findAll();
        session.setAttribute("listProduct", list);
        return "admin/home-update-product";
    }

    @GetMapping("/add/product")
    public String showRegistrationForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    @PostMapping("/add/product")
    public String showRegistrationForm(@RequestParam("name") String name, @RequestParam("description") String description,
                                       @RequestParam("price") String price, @RequestParam("discount") String discount,
                                       @RequestParam("quantity") int quantity, @RequestParam("category") int idCategory,
                                       MultipartFile imageFile, Model model, HttpSession session) {
        List<Category> listCategory = categoryRepository.findAll();
        session.setAttribute("listCategory", listCategory);
        try {
            Category optionCategory = categoryRepository.findById(idCategory).orElse(null);
            Product product = new Product();
            product.setLinkImg("/img/cake-feature/c-feature-6.jpg");
            product.setAmountLeft(quantity);
            product.setAmountSold(0);
            product.setCategory(optionCategory);
            product.setDescription(description);
            product.setDiscount(Float.parseFloat(discount));
            product.setName(name);
            product.setPrice(Float.parseFloat(price));
            product.setQuantity(quantity);
            product.setStatus(1);
            productRepository.save(product);
            model.addAttribute("successAdd", "Sản phẩm đã được thêm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorAdd", "Đã xảy ra lỗi khi thêm sản phẩm!");
        }


        return "admin/add-product";
    }

}
