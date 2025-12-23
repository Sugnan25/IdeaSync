package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CreatorController {

    @Autowired
    private SuggestionService suggestionService;

    // =========================
    // CREATOR DASHBOARD
    // =========================
    @GetMapping("/creator/dashboard")
    public String creatorDashboard(Model model) {

        // Dashboard stats (0 for now – DB later)
        model.addAttribute("totalProjects", 0);
        model.addAttribute("openProjects", 0);
        model.addAttribute("closedProjects", 0);
        model.addAttribute("teamRequests", 0);

        // Default suggestions → TECH
        model.addAttribute("suggestions", suggestionService.technical());
        model.addAttribute("category", "TECH");

        return "creator-dashboard";
    }

    // =========================
    // SWITCH SUGGESTIONS TYPE
    // =========================
    @GetMapping("/creator/suggestions/{type}")
    public String suggestionsByType(@PathVariable String type, Model model) {

        if ("NON_TECH".equalsIgnoreCase(type)) {
            model.addAttribute("suggestions", suggestionService.nonTechnical());
            model.addAttribute("category", "NON_TECH");
        } else {
            model.addAttribute("suggestions", suggestionService.technical());
            model.addAttribute("category", "TECH");
        }

        // Stats again (required for dashboard reload)
        model.addAttribute("totalProjects", 0);
        model.addAttribute("openProjects", 0);
        model.addAttribute("closedProjects", 0);
        model.addAttribute("teamRequests", 0);

        return "creator-dashboard";
    }

    // =========================
    // MY PROJECTS
    // =========================
    @GetMapping("/creator/projects")
    public String myProjects(Model model) {
        model.addAttribute("projects", List.of()); // empty for now
        return "creator-projects";
    }

    // =========================
    // CREATE PROJECT PAGE
    // =========================
    @GetMapping("/creator/create-project")
    public String createProjectPage() {
        return "create-project";
    }

    // =========================
    // TEAM REQUESTS
    // =========================
    @GetMapping("/creator/requests")
    public String teamRequests(Model model) {
        model.addAttribute("requests", List.of()); // empty for now
        return "creator-requests";
    }
}
