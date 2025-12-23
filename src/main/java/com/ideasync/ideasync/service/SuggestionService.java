package com.ideasync.ideasync.service;

import com.ideasync.ideasync.dto.ProjectSuggestion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {

    public List<ProjectSuggestion> technical() {
        return List.of(
            new ProjectSuggestion(
                "Smart Irrigation System",
                "IoT + Sensors based project",
                "smart irrigation system iot project"
            ),
            new ProjectSuggestion(
                "AI Attendance System",
                "Face recognition based system",
                "ai attendance system face recognition"
            ),
            new ProjectSuggestion(
                "Solar Energy Monitor",
                "Renewable energy project",
                "solar energy monitoring project"
            )
        );
    }

    public List<ProjectSuggestion> nonTechnical() {
        return List.of(
            new ProjectSuggestion(
                "Campus Event Management",
                "Plan & manage college events",
                "college event management project"
            ),
            new ProjectSuggestion(
                "Startup Pitch Deck",
                "Business & presentation project",
                "startup pitch deck project"
            ),
            new ProjectSuggestion(
                "Social Media Campaign",
                "Marketing & branding project",
                "social media campaign project"
            )
        );
    }
}
