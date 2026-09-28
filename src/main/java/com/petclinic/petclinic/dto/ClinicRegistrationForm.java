package com.petclinic.petclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClinicRegistrationForm {

    @NotBlank(message = "Please enter the clinic name")
    private String clinicName;

    private String registrationNumber;

    @NotBlank(message = "Please enter a contact email")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Please enter a contact number")
    private String phone;

    @NotBlank(message = "Please enter the clinic address")
    private String address;

    @NotBlank(message = "Please enter the city")
    private String city;

    private String description;

    @NotBlank(message = "Please choose a password")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
}
