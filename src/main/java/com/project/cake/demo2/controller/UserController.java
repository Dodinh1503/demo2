package com.project.cake.demo2.controller;

import com.project.cake.demo2.model.User;
import com.project.cake.demo2.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/inform/customer")
    public String showUpdateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "customer/inform-customer";
    }
    @PostMapping("/inform/customer")
    public String updateUserInfo(@RequestParam("name") String name,
                                 @RequestParam("fullName") String fullName, @RequestParam("email") String email,
                                 Model model, HttpSession session) {
        User updatedUser = new User();
        updatedUser.setUsername(name);
        updatedUser.setName(fullName);
        updatedUser.setEmail(email);

        if (userRepository.findByUsername(updatedUser.getUsername()) != null) {
            // Xử lý logic khi tên người dùng đã tồn tại
            model.addAttribute("error", "Tên người dùng đã đăng ký. Vui lòng điền tên khác.");
            // Ví dụ: Hiển thị thông báo lỗi, chuyển hướng đến trang đăng ký lại
            return "customer/inform-customer";
        } else if (userRepository.findByEmail(updatedUser.getEmail()) != null) {
            model.addAttribute("error", "Email đã đăng ký. Vui lòng điền email khác.");
            return "customer/inform-customer";
        }
        else {
            User existingUser = (User) session.getAttribute("user");

            if (existingUser != null) {
                // Cập nhật thông tin người dùng
                existingUser.setName(updatedUser.getName());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());

                userRepository.save(existingUser); // Lưu thông tin người dùng đã cập nhật
                session.setAttribute("user", updatedUser);
            }

            // Chuyển hướng đến trang hoặc điều hướng phù hợp
            return "customer/inform-customer"; // Thay đổi thành trang cần điều hướng sau khi cập nhật
        }
    }
}