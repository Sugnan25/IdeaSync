package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String dashboard(Model model) {
        long pendingCount = userRepository.countByStatus("PENDING");
        List<User> pendingUsers = userRepository.findByStatus("PENDING");

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("pendingUsers", pendingUsers);

        model.addAttribute("totalUsers", userRepository.count());

        return "admin";
    }

    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            User user = u.get();
            user.setStatus("APPROVED");
            userRepository.save(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/reject/{id}")
    public String rejectUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
