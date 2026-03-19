package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.User;

import org.springframework.security.crypto.password.PasswordEncoder;



@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password) {

        User u = userService.findByEmail(email);

        if (u!=null && passwordEncoder.matches(password, u.getPassword())){
            System.out.println("Login Correcto" + u.getName());
            return "redirect:/Index";
        }

        System.out.println("Login Incorrecto ");
        return "redirect:/Login";

    }
}