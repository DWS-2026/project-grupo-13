package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Mostrar lista de categorías
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoryService.findAll());
        return "admin/categorias"; // Vista Mustache
    }

    // Formulario para crear categoría
    @GetMapping("/nueva")
    public String nuevaCategoriaForm(Model model) {
        model.addAttribute("categoria", new Category());
        return "admin/nueva-categoria";
    }

    // Guardar categoría
    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Category categoria) {
        categoryService.save(categoria);
        return "redirect:/admin/categorias";
    }
}
