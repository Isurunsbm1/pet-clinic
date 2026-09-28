package com.petclinic.petclinic.dto;

import com.petclinic.petclinic.modals.InquiryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InquiryForm {

    @NotBlank(message = "Please give your question a short title")
    private String title;

    @NotNull(message = "Please choose a category")
    private InquiryCategory category;

    @NotBlank(message = "Please describe what's going on")
    private String description;

    private String petName;

    private String petType;

    private String petAge;
}
