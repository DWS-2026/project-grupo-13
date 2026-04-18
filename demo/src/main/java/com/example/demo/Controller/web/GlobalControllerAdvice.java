package com.example.demo.Controller.web;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean logged = auth != null &&
                         auth.isAuthenticated() &&
                         !(auth instanceof AnonymousAuthenticationToken);

        boolean isAdmin = auth != null &&
                          auth.getAuthorities().stream()
                              .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("logged", logged);
        model.addAttribute("isAdmin", isAdmin);
    }
}
