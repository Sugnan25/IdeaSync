package com.ideasync.ideasync.repository;

import com.ideasync.ideasync.entity.Project;
import com.ideasync.ideasync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCreator(User creator);

    long countByStatus(String status);
}
