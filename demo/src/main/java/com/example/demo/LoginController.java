package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password) {

        for (User u : UserController.listaUsuarios) {

            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                System.out.println("LOGIN CORRECTO: " + u.getName());
                return "redirect:/Index";
            }
        }

        System.out.println("LOGIN INCORRECTO");
        return "redirect:/Login";
    }
}