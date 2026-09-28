package com.petclinic.petclinic.services;


import com.petclinic.petclinic.dto.InquiryForm;
import com.petclinic.petclinic.entity.Comment;
import com.petclinic.petclinic.entity.Inquiry;
import com.petclinic.petclinic.entity.PetOwner;
import com.petclinic.petclinic.entity.User;
import com.petclinic.petclinic.modals.InquiryCategory;
import com.petclinic.petclinic.modals.InquiryStatus;
import com.petclinic.petclinic.modals.Role;
import com.petclinic.petclinic.repository.CommentRepository;
import com.petclinic.petclinic.repository.InquiryRepository;
import com.petclinic.petclinic.repository.PetOwnerRepository;
import com.petclinic.petclinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public List<Inquiry> findAll() {
        return inquiryRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Inquiry> findByCategory(InquiryCategory category) {
        return inquiryRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public Inquiry findById(Long id) {
        return inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found (id " + id + ")"));
    }

    public List<Inquiry> findMine(String ownerEmail) {
        PetOwner owner = petOwnerRepository.findByUser_Email(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Pet owner account not found for " + ownerEmail));
        return inquiryRepository.findByPetOwnerOrderByCreatedAtDesc(owner);
    }

    @Transactional
    public Inquiry create(String ownerEmail, InquiryForm form) {
        PetOwner owner = petOwnerRepository.findByUser_Email(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Pet owner account not found for " + ownerEmail));

        Inquiry inquiry = Inquiry.builder()
                .petOwner(owner)
                .title(form.getTitle())
                .description(form.getDescription())
                .category(form.getCategory())
                .petName(form.getPetName())
                .petType(form.getPetType())
                .petAge(form.getPetAge())
                .status(InquiryStatus.OPEN)
                .build();
        return inquiryRepository.save(inquiry);
    }

    /**
     * Adds a reply. If a clinic is the one replying, the inquiry status moves
     * to ANSWERED so both parties can see at a glance that it got a response.
     */
    @Transactional
    public void addComment(Long inquiryId, String authorEmail, String content) {
        Inquiry inquiry = findById(inquiryId);
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for " + authorEmail));

        Comment comment = Comment.builder()
                .inquiry(inquiry)
                .author(author)
                .content(content)
                .build();
        commentRepository.save(comment);

        if (author.getRole() == Role.CLINIC && inquiry.getStatus() == InquiryStatus.OPEN) {
            inquiry.setStatus(InquiryStatus.ANSWERED);
        }
    }
}
