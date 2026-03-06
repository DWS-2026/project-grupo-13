package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/Smartphones")
    public String smartphones(Model model) {

        List<Producto> lista = DataService.getProductos()
                .stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Smartphones"))
                .toList();

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", "Smartphones");

        return "Smartphones";
    }

}