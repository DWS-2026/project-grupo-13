package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;


    //Esto es para buscar los usuarios. Si la cadena del formulario no es null devuelve el buscado. Si es null los devuelve todos.
    @GetMapping("/AdminUser")
    public String adminUsuarios(
            @RequestParam(required = false) String q,
            Model model) {

        List<User> usuarios;

        if (q != null && !q.isEmpty()) {
            usuarios = userRepository.findByNameContainingIgnoreCase(q);
        } else {
            usuarios = userRepository.findAll();
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("q", q);

        return "AdminUser";
    }

    @GetMapping("/AdminUser/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/AdminUser";
    }
}

