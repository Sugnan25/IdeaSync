package com.ideasync.ideasync.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/explorer")
public class ExplorerController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "explorer-dashboard";
    }

    @GetMapping("/projects")
    public String projects() {
        return "explore-projects";
    }

    @GetMapping("/profile")
    public String profile() {
        return "explorer-profile";
    }
}
