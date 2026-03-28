package com.example.demo.Controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ImageService;


@Controller
@RequestMapping("/admin/categorias")
public class CategoryController {

    private final CategoryService categoryService;
    private final ImageService imageService;

    public CategoryController(CategoryService categoryService, ImageService imageService) {
        this.categoryService = categoryService;
        this.imageService = imageService;
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
    public String guardarCategoria(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        Category categoria = new Category();
        categoria.setName(name);

        if (!imageFile.isEmpty()) {
            Image img = imageService.createImage(imageFile);
            categoria.setImage(img);
        }

        categoryService.save(categoria);

        return "redirect:/admin/categorias";
    }
}
