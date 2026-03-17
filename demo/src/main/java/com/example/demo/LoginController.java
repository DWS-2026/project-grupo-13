package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.UserRepository;
import com.example.demo.User;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password) {

        User u = userRepository.findByEmail(email);

        if (u!=null && u.getPassword().equals(password)){
            System.out.println("Login Correcto" + u.getName());
            return "redirect:/Index";
        }

        System.out.println("Login Incorrecto ");
        return "redirect:/Login";


    }
}