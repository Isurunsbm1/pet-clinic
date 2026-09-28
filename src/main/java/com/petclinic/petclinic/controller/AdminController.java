package com.petclinic.petclinic.controller;

import com.petclinic.petclinic.dto.ClinicEditForm;
import com.petclinic.petclinic.dto.OwnerEditForm;
import com.petclinic.petclinic.modals.ClinicStatus;
import com.petclinic.petclinic.repository.InquiryRepository;
import com.petclinic.petclinic.services.ClinicService;
import com.petclinic.petclinic.services.PetOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ClinicService clinicService;
    private final PetOwnerService petOwnerService;
    private final InquiryRepository inquiryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingCount", clinicService.countByStatus(ClinicStatus.PENDING));
        model.addAttribute("approvedCount", clinicService.countByStatus(ClinicStatus.APPROVED));
        model.addAttribute("rejectedCount", clinicService.countByStatus(ClinicStatus.REJECTED));
        model.addAttribute("ownerCount", petOwnerService.findAll().size());
        model.addAttribute("inquiryCount", inquiryRepository.count());
        model.addAttribute("pendingClinics", clinicService.findByStatus(ClinicStatus.PENDING));
        return "admin/dashboard";
    }

    // ---------- Clinics ----------

    @GetMapping("/clinics")
    public String clinics(Model model) {
        model.addAttribute("clinics", clinicService.findAll());
        return "admin/clinics";
    }

    @PostMapping("/clinics/{id}/approve")
    public String approve(@PathVariable Long id, RedirectAttributes ra) {
        clinicService.approve(id);
        ra.addFlashAttribute("successMessage", "Clinic approved. They can now log in.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/clinics/{id}/reject")
    public String reject(@PathVariable Long id, RedirectAttributes ra) {
        clinicService.reject(id);
        ra.addFlashAttribute("successMessage", "Clinic request rejected.");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/clinics/{id}/edit")
    public String editClinicForm(@PathVariable Long id, Model model) {
        var clinic = clinicService.findById(id);
        var form = new ClinicEditForm(clinic.getClinicName(), clinic.getRegistrationNumber(),
                clinic.getAddress(), clinic.getCity(), clinic.getDescription(), clinic.getUser().getPhone());
        model.addAttribute("clinicId", id);
        model.addAttribute("form", form);
        return "admin/clinic-edit";
    }

    @PostMapping("/clinics/{id}/edit")
    public String editClinicSubmit(@PathVariable Long id, @ModelAttribute("form") ClinicEditForm form, RedirectAttributes ra) {
        clinicService.update(id, form);
        ra.addFlashAttribute("successMessage", "Clinic details updated.");
        return "redirect:/admin/clinics";
    }

    @PostMapping("/clinics/{id}/delete")
    public String deleteClinic(@PathVariable Long id, RedirectAttributes ra) {
        clinicService.delete(id);
        ra.addFlashAttribute("successMessage", "Clinic removed from the system.");
        return "redirect:/admin/clinics";
    }

    // ---------- Pet owners ----------

    @GetMapping("/owners")
    public String owners(Model model) {
        model.addAttribute("owners", petOwnerService.findAll());
        return "admin/owners";
    }

    @GetMapping("/owners/{id}/edit")
    public String editOwnerForm(@PathVariable Long id, Model model) {
        var owner = petOwnerService.findById(id);
        var form = new OwnerEditForm(owner.getUser().getFullName(), owner.getUser().getPhone(),
                owner.getAddress(), owner.getCity());
        model.addAttribute("ownerId", id);
        model.addAttribute("form", form);
        return "admin/owner-edit";
    }

    @PostMapping("/owners/{id}/edit")
    public String editOwnerSubmit(@PathVariable Long id, @ModelAttribute("form") OwnerEditForm form, RedirectAttributes ra) {
        petOwnerService.update(id, form);
        ra.addFlashAttribute("successMessage", "Pet owner details updated.");
        return "redirect:/admin/owners";
    }

    @PostMapping("/owners/{id}/delete")
    public String deleteOwner(@PathVariable Long id, RedirectAttributes ra) {
        petOwnerService.delete(id);
        ra.addFlashAttribute("successMessage", "Pet owner removed from the system.");
        return "redirect:/admin/owners";
    }
}

