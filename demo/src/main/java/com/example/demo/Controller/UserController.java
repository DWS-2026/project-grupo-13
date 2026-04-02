package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Image;
import org.springframework.ui.Model;

import com.example.demo.Model.User;
import com.example.demo.Security.RepositoryUserDetailsService;
import com.example.demo.Service.UserService;

import org.springframework.ui.Model;


import org.springframework.security.core.Authentication;

import com.example.demo.Service.ImageService;


import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RepositoryUserDetailsService userDetailsService;



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

        // 🔥 Actualizar sesión
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getNickname());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/EditProfile";
    }
    @PostMapping("/ChangePassword")
    public String changePassword(@RequestParam String oldPassword,
                                @RequestParam String newPassword,
                                Principal principal) {

        User usuario = userService.findByNickname(principal.getName());

        // Validar contraseña antigua
        if (!passwordEncoder.matches(oldPassword, usuario.getEncodedPassword())) {
            return "redirect:/ChangePassword?errorPassword";
        }

        // Guardar nueva contraseña
        usuario.setEncodedPassword(passwordEncoder.encode(newPassword));
        userService.save(usuario);

        // Actualizar sesión
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getNickname());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/EditProfile?passwordChanged";
        

    }

    @GetMapping("/ChangePassword")
    public String changePasswordForm(Model model,
                                    @RequestParam(required = false) String errorPassword) {

        if (errorPassword != null) {
            model.addAttribute("errorPassword", true);
        }

        return "ChangePassword";
    }

    



}
