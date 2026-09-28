package com.petclinic.petclinic.repository;


import com.petclinic.petclinic.entity.Clinic;
import com.petclinic.petclinic.modals.ClinicStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByStatusOrderByRequestedAtDesc(ClinicStatus status);
    List<Clinic> findAllByOrderByClinicNameAsc();
    Optional<Clinic> findByUser_Email(String email);
    long countByStatus(ClinicStatus status);
}
