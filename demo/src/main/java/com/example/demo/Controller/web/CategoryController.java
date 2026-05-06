package com.example.demo.Controller.web;

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

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoryService.findAll());
        return "AdminCategories";
    }

    @GetMapping("/nueva")
    public String nuevaCategoriaForm(Model model) {
        model.addAttribute("categoria", new Category());
        return "admin/nueva-categoria";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile imageFile,
            Model model) throws IOException {

        // 1) Validar nombre vacío
        if (name == null || name.isBlank()) {
            model.addAttribute("errorNombreVacio", true);
            model.addAttribute("categoria", new Category(name));
            return "admin/nueva-categoria";
        }

        // 2) Comprobar duplicado por nombre (case-insensitive si quieres)
        Category existente = categoryService.findByName(name);
        if (existente != null) {
            model.addAttribute("errorDuplicado", true);
            model.addAttribute("nombreFallido", name);
            model.addAttribute("categoria", new Category(name));
            return "admin/nueva-categoria";
        }

        Category categoria = new Category();
        categoria.setName(name); // si quieres, aquí podrías sanitizar

        // 3) Validar imagen (tipo y tamaño) antes de guardar
        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute("errorImagenTipo", true);
                model.addAttribute("categoria", categoria);
                return "admin/nueva-categoria";
            }

            if (imageFile.getSize() > 2_000_000) { // 2 MB
                model.addAttribute("errorImagenTamano", true);
                model.addAttribute("categoria", categoria);
                return "admin/nueva-categoria";
            }

            Image img = imageService.createImage(imageFile);
            categoria.setImage(img);
        }

        categoryService.save(categoria);
        return "redirect:/admin/categorias";
    }
}
