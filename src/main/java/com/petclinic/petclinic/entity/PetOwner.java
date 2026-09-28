package com.petclinic.petclinic.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A pet owner's profile. Pet owners self-register and can post inquiries
 * straight away (no admin approval is required for this role).
 */
@Entity
@Table(name = "pet_owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String address;

    private String city;

    @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Inquiry> inquiries = new ArrayList<>();
}
