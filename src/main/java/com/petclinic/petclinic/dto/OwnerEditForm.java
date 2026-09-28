package com.petclinic.petclinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OwnerEditForm {

    @NotBlank(message = "Please enter the full name")
    private String fullName;

    private String phone;

    private String address;

    private String city;

    public OwnerEditForm(String fullName, String phone, String address, String city) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.city = city;
    }
}
