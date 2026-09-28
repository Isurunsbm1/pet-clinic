package com.petclinic.petclinic.services;


import com.petclinic.petclinic.dto.ClinicRegistrationForm;
import com.petclinic.petclinic.dto.OwnerRegistrationForm;
import com.petclinic.petclinic.entity.Clinic;
import com.petclinic.petclinic.entity.PetOwner;
import com.petclinic.petclinic.entity.User;
import com.petclinic.petclinic.modals.ClinicStatus;
import com.petclinic.petclinic.modals.Role;
import com.petclinic.petclinic.repository.ClinicRepository;
import com.petclinic.petclinic.repository.PetOwnerRepository;
import com.petclinic.petclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Pet owners can start using the platform immediately after signing up.
     */
    @Transactional
    public void registerPetOwner(OwnerRegistrationForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("An account already exists with this email address.");
        }

        User user = User.builder().fullName(form.getFullName()).email(form.getEmail()).password(passwordEncoder.encode(form.getPassword())).phone(form.getPhone()).role(Role.PET_OWNER).enabled(true).build();
        user = userRepository.save(user);

        PetOwner owner = PetOwner.builder().user(user).address(form.getAddress()).city(form.getCity()).build();
        petOwnerRepository.save(owner);
    }

    /**
     * Clinics cannot log in right away. The account is created disabled and the
     * Clinic profile is created with PENDING status until an Admin approves it.
     */
    @Transactional
    public void submitClinicRequest(ClinicRegistrationForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("An account already exists with this email address.");
        }

        User user = User.builder().fullName(form.getClinicName()).email(form.getEmail()).password(passwordEncoder.encode(form.getPassword())).phone(form.getPhone()).role(Role.CLINIC).enabled(false).build();
        user = userRepository.save(user);

        Clinic clinic = Clinic.builder().user(user).clinicName(form.getClinicName()).registrationNumber(form.getRegistrationNumber()).address(form.getAddress()).city(form.getCity()).description(form.getDescription()).status(ClinicStatus.PENDING).build();
        clinicRepository.save(clinic);
    }
}
