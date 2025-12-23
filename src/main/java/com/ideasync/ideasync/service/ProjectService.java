package com.ideasync.ideasync.service;

import com.ideasync.ideasync.entity.Project;
import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getProjectsByCreator(User creator) {
        return projectRepository.findByCreator(creator);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // ✅ ADD THIS
    public long countAllProjects() {
        return projectRepository.count();
    }

    // ✅ ADD THIS (FOR SUPERADMIN PROJECT PAGE)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public long countOpenProjects() {
    return projectRepository.countByStatus("OPEN");
}

    public long countClosedProjects() {
    return projectRepository.countByStatus("CLOSED");
}

}
