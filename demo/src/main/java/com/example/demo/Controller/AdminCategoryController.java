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

    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/AdminCategories")
    public String adminCategories(Model model) {
        model.addAttribute("categorias", categoryRepository.findAll());
        return "AdminCategories";
    }

    //Este método está obsoleto
    @PostMapping("/AdminCategories")
    public String createCategory(@RequestParam String name, 
                                 @RequestParam("image") MultipartFile image, 
                                 Model model) {

        if (categoryRepository.existsByName(name)) {
            model.addAttribute("error", "La categoría '" + name + "' ya ha sido añadida.");
            model.addAttribute("categorias", categoryRepository.findAll());
            return "AdminCategories"; 
        }

        Category c = new Category(name);
        
        categoryRepository.save(c);

        return "redirect:/AdminCategories";
    }

    @GetMapping("/AdminCategories/Delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/AdminCategories";
    }
}