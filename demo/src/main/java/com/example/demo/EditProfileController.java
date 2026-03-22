package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.User;
import com.example.demo.UserService;

@Controller
public class EditProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/EditProfile")
    public String mostrarPerfil(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nickname = auth.getName(); 
        User usuario = userService.findByNickname(nickname);

        model.addAttribute("usuario", usuario);

        return "EditProfile"; 
    }
}
