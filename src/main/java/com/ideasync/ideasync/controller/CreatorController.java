package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.entity.Project;
import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.ProjectRepository;
import com.ideasync.ideasync.repository.UserRepository;
import com.ideasync.ideasync.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
public class CreatorController {

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Use a directory outside the JAR/Classpath
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

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

    @PostMapping("/creator/create-project")
    public String createProject(@ModelAttribute Project project,
                                @RequestParam("image") MultipartFile image,
                                Principal principal) {

        if (principal != null) {
            String email = principal.getName();
            User creator = userRepository.findByEmail(email).orElse(null);
            project.setCreator(creator);
        }

        project.setStatus("OPEN");

        if (!image.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath);

                project.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        projectRepository.save(project);
        return "redirect:/creator/projects";
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
