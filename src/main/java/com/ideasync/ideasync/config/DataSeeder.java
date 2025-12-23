package com.ideasync.ideasync.config;

import com.ideasync.ideasync.entity.User;
import com.ideasync.ideasync.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed Super Admin
        if (userRepository.findByEmail("super@ideasync.com").isEmpty()) {
            User superAdmin = new User();
            superAdmin.setName("Super Admin");
            superAdmin.setEmail("super@ideasync.com");
            superAdmin.setPassword("Sugnan@#25");
            superAdmin.setRole("SUPER_ADMIN");
            superAdmin.setStatus("APPROVED"); // Auto-approved

            userRepository.save(superAdmin);
            System.out.println("✅ Super Admin user created: super@ideasync.com");
        }
    }
}
