package com.example.demo;

import java.util.List;
import com.example.demo.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/categoria/{nombre}")
    public String categoria(@PathVariable String nombre, Model model) {

        List<Product> lista = productService.findByCategoryName(nombre);

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", nombre);

        return "Categories"; // Categoria.html
    }

    @GetMapping("/producto/{id}")
    public String producto(@PathVariable int id, Model model) {

       Product p = productService.findById(id).orElse(null);


        model.addAttribute("producto", p);

        return "Product"; // producto.html
    }

}