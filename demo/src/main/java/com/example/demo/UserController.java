package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.example.demo.UserRepository;
import com.example.demo.User;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //public static List<User> listaUsuarios = new ArrayList<>();

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute User usuarioRegistrado) {
        //listaUsuarios.add(usuarioRegistrado);
        userRepository.save(usuarioRegistrado);
        
        System.out.println("-------------------------------------------");
        System.out.println("¡USUARIO GUARDADO EN LA LISTA!");
        System.out.println("Nombre: " + usuarioRegistrado.getName());
        System.out.println("Fecha: " + usuarioRegistrado.getBirthDate());
        System.out.println("-------------------------------------------");
        
        return "redirect:/Login"; 
    }

    @GetMapping("/ver-usuarios")
    @ResponseBody 
    public List<User> verUsuariosRegistrados() {
        return userRepository.findAll();
    }
}