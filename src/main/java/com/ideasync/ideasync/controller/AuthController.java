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
        return "redirect:/login";
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

    // ✅ SIGNUP PAGE
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // ✅ SIGNUP LOGIC
    @PostMapping("/signup")
    public String signup(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String role,
                         Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            return "signup";
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setStatus("PENDING"); // Waits for Admin Approval

        userRepository.save(newUser);

        model.addAttribute("message", "Account created successfully! Wait for Admin Approval.");
        return "signup"; // Stay on signup page or redirect to login
    }
}
