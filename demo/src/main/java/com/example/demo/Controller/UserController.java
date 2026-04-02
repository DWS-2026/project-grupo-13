package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Image;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

import org.springframework.ui.Model;



import com.example.demo.Service.ImageService;


import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute User usuarioRegistrado) {

        // Cifrar contraseña
        usuarioRegistrado.setEncodedPassword(
            passwordEncoder.encode(usuarioRegistrado.getEncodedPassword())
        );

        // Asignar rol por defecto
        usuarioRegistrado.setRole("USER");

        userService.save(usuarioRegistrado);

        return "redirect:/Login";
    }

    @GetMapping("/ver-usuarios")
    @ResponseBody 
    public List<User> verUsuariosRegistrados() {
        return userService.findAll();
    }

    @PostMapping("/user/profile/upload")
        public String uploadProfileImage(
        @RequestParam("profileImage") MultipartFile file,
        Principal principal) throws IOException {

            User user = userService.findByNickname(principal.getName());


            if (!file.isEmpty()) {
                Image img = imageService.createImage(file);
                user.setProfileImage(img);
                userService.save(user);
            }

            return "redirect:/EditProfile";
    }

    @GetMapping("/Profile/Edit")
    public String mostrarPerfil(Model model, Principal principal) {
        User user = userService.findByNickname(principal.getName());
        model.addAttribute("user", user);
        return "EditProfile";  // ← AQUÍ ESTABA EL ERROR
    }



}
