package com.petclinic.petclinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClinicEditForm {

    @NotBlank(message = "Please enter the clinic name")
    private String clinicName;

    private String registrationNumber;

    @NotBlank(message = "Please enter the clinic address")
    private String address;

    @NotBlank(message = "Please enter the city")
    private String city;

    private String description;

    private String phone;

    public ClinicEditForm(String clinicName, String registrationNumber, String address,
                          String city, String description, String phone) {
        this.clinicName = clinicName;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.city = city;
        this.description = description;
        this.phone = phone;
    }
}
