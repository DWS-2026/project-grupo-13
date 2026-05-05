package com.example.demo.Controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Image;
import com.example.demo.Model.User;
import com.example.demo.Security.RepositoryUserDetailsService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.ImageService;

import java.io.IOException;
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
        usuarioRegistrado.setEncodedPassword(passwordEncoder.encode(usuarioRegistrado.getEncodedPassword()));
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
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file, Principal principal) throws IOException {
        User user = userService.findByNickname(principal.getName());
        if (!file.isEmpty()) {
            Image img = imageService.createImage(file);
            user.setProfileImage(img);
            userService.save(user);
        }
        return "redirect:/EditProfile";
    }

    @GetMapping("/UserProfileView") 
    public String mostrarPerfil(Model model, Principal principal) {
        User user = userService.findByNickname(principal.getName());
        model.addAttribute("usuario", user);
        return "EditProfile";
    }

    @GetMapping("/EditData")
    public String mostrarEditarDatos(Model model, Principal principal) {
        User usuario = userService.findByNickname(principal.getName());
        model.addAttribute("usuario", usuario);
        return "EditData";
    }

    @PostMapping("/EditData")
    public String editarDatos(@RequestParam String nickname, @RequestParam String name, @RequestParam String surname,
                              @RequestParam String email, @RequestParam(required = false) String password,
                              @RequestParam String birthDate, Principal principal, Model model) {
        User usuario = userService.findByNickname(principal.getName());
        //usuario.setNickname(nickname);
        usuario.setName(name);
        usuario.setSurname(surname);
        usuario.setEmail(email);
        usuario.setBirthDate(LocalDate.parse(birthDate));

        
        User otro = userService.findByEmail(email);

        if (otro != null && !(otro.getId().equals(usuario.getId()))) {
            model.addAttribute("errorEmailDuplicado", true);
            model.addAttribute("emailFallido", email);
            model.addAttribute("usuario", usuario);
            return "EditData";
        }



        if (password != null && !password.isBlank()) {
            usuario.setEncodedPassword(passwordEncoder.encode(password));
        }

        userService.save(usuario);
        actualizarSesion(usuario);
        return "redirect:/EditProfile";
    }

    @GetMapping("/ChangePassword")
    public String changePasswordForm(Model model) {
        return "ChangePassword";
    }

    @PostMapping("/ChangePassword")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, 
                                 Principal principal, Model model) {
        User usuario = userService.findByNickname(principal.getName());

        if (!passwordEncoder.matches(oldPassword, usuario.getEncodedPassword())) {
            model.addAttribute("errorPassword", true);
            return "ChangePassword";
        }

        usuario.setEncodedPassword(passwordEncoder.encode(newPassword));
        userService.save(usuario);
        
        actualizarSesion(usuario);
        return "redirect:/EditProfile?passwordChanged";
    }

    private void actualizarSesion(User usuario) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getNickname());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}