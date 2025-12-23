package com.ideasync.ideasync.service;

import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public long countAllUsers() {
        return userRepo.count();
    }

    public long countPendingUsers() {
        return userRepo.countByStatus("PENDING");
    }

    public long countRejectedUsers() {
        return userRepo.countByStatus("REJECTED");
    }

    public long countApprovedUsers() {
        return userRepo.countByStatus("APPROVED");
    }

    public long countCreators() {
        return userRepo.countByRole("PROJECT_CREATOR");
    }

    public long countExplorers() {
        return userRepo.countByRole("PROJECT_EXPLORER");
    }

    public List<User> getPendingUsers() {
        return userRepo.findByStatus("PENDING");
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void updateStatus(Long userId, String status) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            user.setStatus(status);
            userRepo.save(user);
        }
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }
}
