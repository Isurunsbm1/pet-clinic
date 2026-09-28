package com.petclinic.petclinic.config;

import com.petclinic.petclinic.entity.User;
import com.petclinic.petclinic.modals.Role;
import com.petclinic.petclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@vetconnect.lk";
    private static final String ADMIN_DEFAULT_PASSWORD = "Admin@123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
            User admin = User.builder()
                    .fullName("System Administrator")
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_DEFAULT_PASSWORD))
                    .phone("0110000000")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);

            System.out.println("=============================================================");
            System.out.println(" Default admin account created:");
            System.out.println("   email:    " + ADMIN_EMAIL);
            System.out.println("   password: " + ADMIN_DEFAULT_PASSWORD);
            System.out.println(" Please log in and note this down. Change it in MySQL for production use.");
            System.out.println("=============================================================");
        }
    }
}
