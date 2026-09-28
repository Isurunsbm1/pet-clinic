package com.petclinic.petclinic.controller;


import com.petclinic.petclinic.modals.ClinicStatus;
import com.petclinic.petclinic.services.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClinicDirectoryController {

    private final ClinicService clinicService;

    @GetMapping("/clinics")
    public String list(Model model) {
        model.addAttribute("clinics", clinicService.findByStatus(ClinicStatus.APPROVED));
        return "clinic-directory";
    }
}
