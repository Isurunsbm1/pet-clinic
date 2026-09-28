package com.petclinic.petclinic.servlet;


import com.petclinic.petclinic.entity.Comment;
import com.petclinic.petclinic.entity.Inquiry;
import com.petclinic.petclinic.repository.InquiryRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Plain jakarta.servlet.http.HttpServlet (not a Spring @Controller) that lets
 * a logged-in user download a plain-text summary of one inquiry thread -
 * useful to print or keep for a vet visit. Registered manually in
 * {@link .config.ServletConfig} alongside Spring MVC,
 * demonstrating the raw Servlet API running inside the same embedded Tomcat.
 *
 * Mapped to: GET /servlet/inquiry-summary/{id}
 */
public class InquirySummaryServlet extends HttpServlet {

    private final InquiryRepository inquiryRepository;

    public InquirySummaryServlet(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // e.g. "/5"
        Long id;
        try {
            id = Long.parseLong(pathInfo.substring(1));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inquiry id");
            return;
        }

        Optional<Inquiry> found = inquiryRepository.findById(id);
        if (found.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Inquiry not found");
            return;
        }
        Inquiry inquiry = found.get();

        resp.setContentType("text/plain; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=\"inquiry-" + id + "-summary.txt\"");

        try (PrintWriter out = resp.getWriter()) {
            out.println("VETCONNECT SRI LANKA - CONSULTATION SUMMARY");
            out.println("============================================");
            out.println("Inquiry #:  " + inquiry.getId());
            out.println("Title:      " + inquiry.getTitle());
            out.println("Category:   " + inquiry.getCategory());
            out.println("Pet:        " + safe(inquiry.getPetName()) + " (" + safe(inquiry.getPetType()) + ", " + safe(inquiry.getPetAge()) + ")");
            out.println("Posted by:  " + inquiry.getPetOwner().getUser().getFullName());
            out.println("Date:       " + inquiry.getCreatedAt());
            out.println("Status:     " + inquiry.getStatus());
            out.println();
            out.println("Question:");
            out.println(inquiry.getDescription());
            out.println();
            out.println("--------------------------------------------");
            out.println("Responses:");

            if (inquiry.getComments().isEmpty()) {
                out.println("(No responses yet.)");
            } else {
                for (Comment c : inquiry.getComments()) {
                    out.println();
                    out.println("[" + c.getCreatedAt() + "] " + c.getAuthor().getFullName()
                            + " (" + c.getAuthor().getRole() + ")");
                    out.println(c.getContent());
                }
            }
            out.flush();
        }
    }

    private String safe(String s) {
        return (s == null || s.isBlank()) ? "N/A" : s;
    }
}
