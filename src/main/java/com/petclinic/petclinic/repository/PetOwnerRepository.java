package com.petclinic.petclinic.repository;

import com.petclinic.petclinic.entity.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
    Optional<PetOwner> findByUser_Email(String email);
    List<PetOwner> findAllByOrderByIdDesc();
}