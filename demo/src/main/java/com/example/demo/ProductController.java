package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @GetMapping("/categoria/{nombre}")
    public String categoria(@PathVariable String nombre, Model model) {

        List<Producto> lista = DataService.getProductos()
            .stream()
            .filter(p -> p.getCategoria().equalsIgnoreCase(nombre))
            .toList();

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", nombre);

        return "Categories"; // Categoria.html
    }

    @GetMapping("/producto/{id}")
    public String producto(@PathVariable int id, Model model) {

        Producto p = DataService.getProductos()
            .stream()
            .filter(prod -> prod.getId() == id)
            .findFirst()
            .orElse(null);

        model.addAttribute("producto", p);

        return "Product"; // producto.html
    }

}