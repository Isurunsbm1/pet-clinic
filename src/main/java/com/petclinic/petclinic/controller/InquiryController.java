package com.petclinic.petclinic.controller;

import com.petclinic.petclinic.dto.InquiryForm;
import com.petclinic.petclinic.entity.Inquiry;
import com.petclinic.petclinic.modals.InquiryCategory;
import com.petclinic.petclinic.services.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    /** The community Q&A board - every registered clinic and pet owner can browse it. */
    @GetMapping
    public String list(@RequestParam(required = false) InquiryCategory category, Model model) {
        model.addAttribute("inquiries", category != null ? inquiryService.findByCategory(category) : inquiryService.findAll());
        model.addAttribute("categories", InquiryCategory.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("pageTitle", "Community Q&A");
        return "inquiry/list";
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('PET_OWNER')")
    public String mine(Authentication auth, Model model) {
        model.addAttribute("inquiries", inquiryService.findMine(auth.getName()));
        model.addAttribute("categories", InquiryCategory.values());
        model.addAttribute("pageTitle", "My Questions");
        model.addAttribute("mineView", true);
        return "inquiry/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('PET_OWNER')")
    public String newForm(Model model) {
        model.addAttribute("form", new InquiryForm());
        model.addAttribute("categories", InquiryCategory.values());
        return "inquiry/new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('PET_OWNER')")
    public String create(@Valid @ModelAttribute("form") InquiryForm form, BindingResult bindingResult,
                         Authentication auth, Model model, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", InquiryCategory.values());
            return "inquiry/new";
        }
        Inquiry inquiry = inquiryService.create(auth.getName(), form);
        ra.addFlashAttribute("successMessage", "Your question has been posted to registered clinics.");
        return "redirect:/inquiries/" + inquiry.getId();
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("inquiry", inquiryService.findById(id));
        return "inquiry/view";
    }

    @PostMapping("/{id}/comment")
    public String comment(@PathVariable Long id, @RequestParam String content, Authentication auth, RedirectAttributes ra) {
        if (content == null || content.isBlank()) {
            ra.addFlashAttribute("errorMessage", "Please write a reply before submitting.");
            return "redirect:/inquiries/" + id;
        }
        inquiryService.addComment(id, auth.getName(), content.trim());
        ra.addFlashAttribute("successMessage", "Your reply has been posted.");
        return "redirect:/inquiries/" + id;
    }
}

