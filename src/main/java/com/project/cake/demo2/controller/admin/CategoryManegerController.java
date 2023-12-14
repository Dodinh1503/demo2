package com.project.cake.demo2.controller.admin;

import com.project.cake.demo2.model.Category;
import com.project.cake.demo2.model.CategoryParent;
import com.project.cake.demo2.model.Product;
import com.project.cake.demo2.repository.CategoryParentRepository;
import com.project.cake.demo2.repository.CategoryRepository;
import com.project.cake.demo2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryManegerController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryParentRepository categoryParentRepository;
    @GetMapping("/add/category")
    private String viewAddCategory(Model model, HttpSession session){
        List<CategoryParent> listCategoryParent = categoryParentRepository.findAll();
        session.setAttribute("listCategoryParent", listCategoryParent);
        model.addAttribute("category", new Category());
        return "admin/add-category";
    }

    @PostMapping("/add/category")
    private String addCategory(@RequestParam("name") String name, @RequestParam("categoryParent") int idCategoryParent,
                               Model model){
        try {
            CategoryParent optionCategoryParent = categoryParentRepository.findById(idCategoryParent).orElse(null);
            Category category = new Category();
            category.setName(name);
            category.setCategoryParent(optionCategoryParent);
            category.setStatus(1);
            categoryRepository.save(category);
            model.addAttribute("successAdd", "Danh mục sản phẩm đã được thêm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorAdd", "Đã xảy ra lỗi khi thêm danh mục sản phẩm!");
        }
        return "admin/add-category";
    }
}
