package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

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
}
