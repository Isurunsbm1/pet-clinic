package com.petclinic.petclinic.controller;


import com.petclinic.petclinic.dto.ClinicRegistrationForm;
import com.petclinic.petclinic.dto.OwnerRegistrationForm;
import com.petclinic.petclinic.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/register/owner")
    public String ownerForm(Model model) {
        model.addAttribute("form", new OwnerRegistrationForm());
        return "register-owner";
    }

    @PostMapping("/register/owner")
    public String ownerSubmit(@Valid @ModelAttribute("form") OwnerRegistrationForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
        }
        if (bindingResult.hasErrors()) {
            return "register-owner";
        }
        try {
            registrationService.registerPetOwner(form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register-owner";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Account created! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/register/clinic")
    public String clinicForm(Model model) {
        model.addAttribute("form", new ClinicRegistrationForm());
        return "register-clinic";
    }

    @PostMapping("/register/clinic")
    public String clinicSubmit(@Valid @ModelAttribute("form") ClinicRegistrationForm form, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
        }
        if (bindingResult.hasErrors()) {
            return "register-clinic";
        }
        try {
            registrationService.submitClinicRequest(form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register-clinic";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Your registration request has been submitted. You'll be able to log in once an administrator approves your clinic.");
        return "redirect:/login";
    }
}
