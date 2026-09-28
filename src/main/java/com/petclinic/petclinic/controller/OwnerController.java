package com.petclinic.petclinic.controller;

import com.petclinic.petclinic.dto.OwnerEditForm;
import com.petclinic.petclinic.entity.Inquiry;
import com.petclinic.petclinic.entity.PetOwner;
import com.petclinic.petclinic.modals.InquiryStatus;
import com.petclinic.petclinic.services.InquiryService;
import com.petclinic.petclinic.services.PetOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final PetOwnerService petOwnerService;
    private final InquiryService inquiryService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        PetOwner owner = petOwnerService.findByEmail(auth.getName());
        List<Inquiry> myInquiries = inquiryService.findMine(auth.getName());
        model.addAttribute("owner", owner);
        model.addAttribute("myInquiries", myInquiries);
        model.addAttribute("openCount", myInquiries.stream().filter(i -> i.getStatus() == InquiryStatus.OPEN).count());
        model.addAttribute("answeredCount", myInquiries.stream().filter(i -> i.getStatus() == InquiryStatus.ANSWERED).count());
        return "owner/dashboard";
    }

    @GetMapping("/profile")
    public String profileForm(Authentication auth, Model model) {
        PetOwner owner = petOwnerService.findByEmail(auth.getName());
        var form = new OwnerEditForm(owner.getUser().getFullName(), owner.getUser().getPhone(), owner.getAddress(), owner.getCity());
        model.addAttribute("owner", owner);
        model.addAttribute("form", form);
        return "owner/profile";
    }

    @PostMapping("/profile")
    public String profileSubmit(Authentication auth, @ModelAttribute("form") OwnerEditForm form, RedirectAttributes ra) {
        petOwnerService.updateOwnProfile(auth.getName(), form);
        ra.addFlashAttribute("successMessage", "Your profile has been updated.");
        return "redirect:/owner/profile";
    }
}
