package com.petclinic.petclinic.services;


import com.petclinic.petclinic.dto.ClinicEditForm;
import com.petclinic.petclinic.entity.Clinic;
import com.petclinic.petclinic.modals.ClinicStatus;
import com.petclinic.petclinic.repository.ClinicRepository;
import com.petclinic.petclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public List<Clinic> findAll() {
        return clinicRepository.findAllByOrderByClinicNameAsc();
    }

    public List<Clinic> findByStatus(ClinicStatus status) {
        return clinicRepository.findByStatusOrderByRequestedAtDesc(status);
    }

    public long countByStatus(ClinicStatus status) {
        return clinicRepository.countByStatus(status);
    }

    public Clinic findById(Long id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found (id " + id + ")"));
    }

    public Clinic findByEmail(String email) {
        return clinicRepository.findByUser_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Clinic account not found for " + email));
    }

    /**
     * Admin approves a pending request: the clinic's login is switched on.
     */
    @Transactional
    public void approve(Long id) {
        Clinic clinic = findById(id);
        clinic.setStatus(ClinicStatus.APPROVED);
        clinic.setApprovedAt(LocalDateTime.now());
        clinic.getUser().setEnabled(true);
    }

    /**
     * Admin rejects a pending request: the login stays disabled.
     */
    @Transactional
    public void reject(Long id) {
        Clinic clinic = findById(id);
        clinic.setStatus(ClinicStatus.REJECTED);
        clinic.getUser().setEnabled(false);
    }

    /**
     * Admin removes a clinic (and its user account) from the system entirely.
     */
    @Transactional
    public void delete(Long id) {
        Clinic clinic = findById(id);
        userRepository.delete(clinic.getUser());
    }

    /**
     * Used by the Admin "edit clinic" screen.
     */
    @Transactional
    public void update(Long id, ClinicEditForm form) {
        applyForm(findById(id), form);
    }

    /**
     * Used by the clinic's own "edit my profile" screen.
     */
    @Transactional
    public void updateOwnProfile(String email, ClinicEditForm form) {
        applyForm(findByEmail(email), form);
    }

    private void applyForm(Clinic clinic, ClinicEditForm form) {
        clinic.setClinicName(form.getClinicName());
        clinic.setRegistrationNumber(form.getRegistrationNumber());
        clinic.setAddress(form.getAddress());
        clinic.setCity(form.getCity());
        clinic.setDescription(form.getDescription());
        clinic.getUser().setPhone(form.getPhone());
    }
}
