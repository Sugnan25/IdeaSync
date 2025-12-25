package com.ideasync.ideasync.controller;

import com.ideasync.ideasync.entity.Project;
import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.ProjectRepository;
import com.ideasync.ideasync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/superadmin")
public class Superadmincontroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Use a directory outside the JAR/Classpath
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("pendingUsers", userRepository.countByStatus("PENDING"));
        model.addAttribute("rejectedUsers", userRepository.countByStatus("REJECTED"));
        model.addAttribute("totalProjects", projectRepository.count());
        return "superadmin-dashboard";
    }

    @GetMapping("/pending-approvals")
    public String pendingApprovals(Model model) {
        model.addAttribute("pendingUsers", userRepository.findByStatus("PENDING"));
        return "pending-approvals";
    }

    @GetMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setStatus("APPROVED");
            userRepository.save(user);
        }
        return "redirect:/superadmin/pending-approvals";
    }

    @GetMapping("/reject/{id}")
    public String rejectUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setStatus("REJECTED");
            userRepository.save(user);
        }
        return "redirect:/superadmin/pending-approvals";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "all-users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/superadmin/users";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "all-projects";
    }

    @PostMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/superadmin/projects";
    }

    @GetMapping("/create-project")
    public String createProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "superadmin-create-project";
    }

    @PostMapping("/create-project")
    public String createProject(@ModelAttribute Project project,
                                @RequestParam("image") MultipartFile image,
                                Principal principal) {

        // Find the current admin user to assign as creator (or system user)
        // For SuperAdmin creating projects, we might assign it to them
        if (principal != null) {
            String email = principal.getName();
            User admin = userRepository.findByEmail(email).orElse(null);
            project.setCreator(admin);
        }

        project.setStatus("OPEN");

        if (!image.isEmpty()) {
            try {
                // Ensure directory exists
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
        return "redirect:/superadmin/projects";
    }
}
