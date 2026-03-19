package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.example.demo.User;


@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserService userService;

    //public static List<User> listaUsuarios = new ArrayList<>();

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute User usuarioRegistrado) {

        usuarioRegistrado.setPassword(
            passwordEncoder.encode(usuarioRegistrado.getPassword())
        );

        userService.save(usuarioRegistrado);

        return "redirect:/Login";
    }


    @GetMapping("/ver-usuarios")
    @ResponseBody 
    public List<User> verUsuariosRegistrados() {
        return userService.findAll();
    }
}