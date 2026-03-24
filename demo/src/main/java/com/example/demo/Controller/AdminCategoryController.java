package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Category;
import com.example.demo.Repository.CategoryRepository; // Importamos el Repositorio

@Controller
public class AdminCategoryController {

    // Inyectamos directamente el repositorio
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/AdminCategories")
    public String adminCategories(Model model) {
        model.addAttribute("categorias", categoryRepository.findAll());
        return "AdminCategories";
    }

    @PostMapping("/AdminCategories")
    public String createCategory(@RequestParam String name, 
                                 @RequestParam("image") MultipartFile image, 
                                 Model model) {

        // 1. Validar si la categoría ya existe llamando al repositorio
        if (categoryRepository.existsByName(name)) {
            model.addAttribute("error", "La categoría '" + name + "' ya ha sido añadida.");
            model.addAttribute("categorias", categoryRepository.findAll());
            return "AdminCategories"; 
        }

        // 2. Crear y guardar la categoría
        Category c = new Category(name);
        
        // Si tienes la imagen en el modelo, sería algo así:
        // c.setImageName(image.getOriginalFilename());
        
        categoryRepository.save(c);

        // 3. TODO: Aquí iría tu código para guardar el archivo físico (la foto)
        // igual que lo tienes en productos.

        return "redirect:/AdminCategories";
    }

    @GetMapping("/AdminCategories/Delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/AdminCategories";
    }
}