package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "interface";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Email or Password");
            return "interface";
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid Email or Password");
            return "interface";
        }

        if (!"APPROVED".equalsIgnoreCase(user.getStatus())) {
            model.addAttribute("error", "Account awaiting admin approval");
            return "interface";
        }

        // ✅ SET SESSION
        session.setAttribute("USER_EMAIL", user.getEmail());
        session.setAttribute("USER_ROLE", user.getRole());

        // ✅ REDIRECT
        if (user.getRole().equals("SUPER_ADMIN")) {
            return "redirect:/superadmin/dashboard";
        }
        if (user.getRole().equals("PROJECT_CREATOR")) {
            return "redirect:/creator/dashboard";
        }
        if (user.getRole().equals("PROJECT_EXPLORER")) {
            return "redirect:/explorer/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
