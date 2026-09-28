package com.petclinic.petclinic.controller;

import com.petclinic.petclinic.dto.ClinicEditForm;
import com.petclinic.petclinic.entity.Clinic;
import com.petclinic.petclinic.modals.InquiryStatus;
import com.petclinic.petclinic.repository.CommentRepository;
import com.petclinic.petclinic.repository.InquiryRepository;
import com.petclinic.petclinic.services.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;
    private final InquiryRepository inquiryRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        Clinic clinic = clinicService.findByEmail(auth.getName());
//        List<var_openInquiries> unused = null; // placeholder never used - removed below
        model.addAttribute("clinic", clinic);
        model.addAttribute("openInquiries", inquiryRepository.findAllByOrderByCreatedAtDesc()
                .stream().filter(i -> i.getStatus() == InquiryStatus.OPEN).limit(10).toList());
        model.addAttribute("myAnswerCount", commentRepository.countByAuthor(clinic.getUser()));
        return "clinic/dashboard";
    }

    @GetMapping("/profile")
    public String profileForm(Authentication auth, Model model) {
        Clinic clinic = clinicService.findByEmail(auth.getName());
        var form = new ClinicEditForm(clinic.getClinicName(), clinic.getRegistrationNumber(),
                clinic.getAddress(), clinic.getCity(), clinic.getDescription(), clinic.getUser().getPhone());
        model.addAttribute("clinic", clinic);
        model.addAttribute("form", form);
        return "clinic/profile";
    }

    @PostMapping("/profile")
    public String profileSubmit(Authentication auth, @ModelAttribute("form") ClinicEditForm form, RedirectAttributes ra) {
        clinicService.updateOwnProfile(auth.getName(), form);
        ra.addFlashAttribute("successMessage", "Your clinic profile has been updated.");
        return "redirect:/clinic/profile";
    }
}
