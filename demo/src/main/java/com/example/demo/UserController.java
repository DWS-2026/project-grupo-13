package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static List<User> listaUsuarios = new ArrayList<>();

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute User usuarioRegistrado) {
        listaUsuarios.add(usuarioRegistrado);
        
        System.out.println("-------------------------------------------");
        System.out.println("¡USUARIO GUARDADO EN LA LISTA!");
        System.out.println("Nombre: " + usuarioRegistrado.getName());
        System.out.println("Total de usuarios registrados: " + listaUsuarios.size());
        System.out.println("-------------------------------------------");
        
        return "redirect:/Login"; 
    }

    @GetMapping("/ver-usuarios")
    @ResponseBody 
    public List<User> verUsuariosRegistrados() {
        return listaUsuarios;
    }
}