package com.petclinic.petclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OwnerRegistrationForm {

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @NotBlank(message = "Please enter your email address")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Please enter a contact number")
    private String phone;

    private String address;

    private String city;

    @NotBlank(message = "Please choose a password")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
}
