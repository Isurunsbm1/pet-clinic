package com.petclinic.petclinic.services;


import com.petclinic.petclinic.dto.OwnerEditForm;
import com.petclinic.petclinic.entity.PetOwner;
import com.petclinic.petclinic.repository.PetOwnerRepository;
import com.petclinic.petclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetOwnerService {

    private final PetOwnerRepository petOwnerRepository;
    private final UserRepository userRepository;

    public List<PetOwner> findAll() {
        return petOwnerRepository.findAllByOrderByIdDesc();
    }

    public PetOwner findById(Long id) {
        return petOwnerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet owner not found (id " + id + ")"));
    }

    public PetOwner findByEmail(String email) {
        return petOwnerRepository.findByUser_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Pet owner account not found for " + email));
    }

    /** Admin removes a pet owner (and their user account, inquiries and comments). */
    @Transactional
    public void delete(Long id) {
        PetOwner owner = findById(id);
        userRepository.delete(owner.getUser());
    }

    @Transactional
    public void update(Long id, OwnerEditForm form) {
        applyForm(findById(id), form);
    }

    @Transactional
    public void updateOwnProfile(String email, OwnerEditForm form) {
        applyForm(findByEmail(email), form);
    }

    private void applyForm(PetOwner owner, OwnerEditForm form) {
        owner.getUser().setFullName(form.getFullName());
        owner.getUser().setPhone(form.getPhone());
        owner.setAddress(form.getAddress());
        owner.setCity(form.getCity());
    }
}

