package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/AdminCategories")
    public String adminCategories(Model model) {
        model.addAttribute("categorias", categoryService.findAll());
        return "AdminCategories";
    }

    @PostMapping("/AdminCategories")
    public String createCategory(@RequestParam String name) {

        Category c = new Category(name);
        categoryService.save(c);

        return "redirect:/AdminCategories";
    }

    @GetMapping("/AdminCategories/Delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteById(id);

        return "redirect:/AdminCategories";
    }

}
