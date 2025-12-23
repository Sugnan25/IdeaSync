package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Superadmincontroller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/superadmin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("pendingUsers", userRepository.countByStatus("PENDING"));
        model.addAttribute("rejectedUsers", userRepository.countByStatus("REJECTED"));
        model.addAttribute("totalProjects", 0);
        return "superadmin-dashboard";
    }

    @GetMapping("/superadmin/pending-approvals")
    public String pendingApprovals() {
        return "pending-approvals";
    }

    @GetMapping("/superadmin/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "all-users";
    }
    @GetMapping("/superadmin/projects")
    public String projects() {
        return "all-projects";
    }
}
