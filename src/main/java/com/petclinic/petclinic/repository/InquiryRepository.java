package com.petclinic.petclinic.repository;

import com.petclinic.petclinic.entity.Inquiry;
import com.petclinic.petclinic.entity.PetOwner;
import com.petclinic.petclinic.modals.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByOrderByCreatedAtDesc();
    List<Inquiry> findByCategoryOrderByCreatedAtDesc(InquiryCategory category);
    List<Inquiry> findByPetOwnerOrderByCreatedAtDesc(PetOwner petOwner);
    long countByPetOwner(PetOwner petOwner);
}
