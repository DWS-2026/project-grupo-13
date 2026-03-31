package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
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

    //Para editar datos del perfil del usuario logeado
    @GetMapping("/EditData")
    public String mostrarEditarDatos(Model model, Principal principal) {

        User usuario = userService.findByNickname(principal.getName());
        model.addAttribute("usuario", usuario);

        return "EditData";
    }

    @PostMapping("/EditData")
    public String editarDatos(@RequestParam String nickname,
                            @RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String email,
                            @RequestParam(required = false) String password,
                            @RequestParam String birthDate,
                            Principal principal) {

        User usuario = userService.findByNickname(principal.getName());

        usuario.setNickname(nickname);
        usuario.setName(name);
        usuario.setSurname(surname);
        usuario.setEmail(email);
        usuario.setBirthDate(LocalDate.parse(birthDate));

        if (password != null && !password.isBlank()) {
            usuario.setEncodedPassword(passwordEncoder.encode(password));
        }

        userService.save(usuario);

        return "redirect:/EditProfile";
    }

}
