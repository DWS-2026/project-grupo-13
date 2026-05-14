package com.example.demo.Controller.web;

import java.security.Principal;
import java.time.LocalDate;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Service.CategoryService;
import com.example.demo.dto.CategoryCreateDTO;

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
    public String createCategory(@RequestParam String name,
                                @RequestParam("image") MultipartFile image,
                                Model model) {

        CategoryCreateDTO dto = new CategoryCreateDTO(name, image);

        try {
            categoryService.createCategory(dto);
            return "redirect:/AdminCategories";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categorias", categoryService.findAll());
            return "AdminCategories";
        }
    }

    @GetMapping("/AdminCategories/Delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/AdminCategories";
    }

    @GetMapping("/AdminCategories/Edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "EditCategory";
    }

    @PostMapping("/AdminCategories/Edit/{id}")
    public String updateCategory(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam("image") MultipartFile file) {

        CategoryCreateDTO dto = new CategoryCreateDTO(name, file);

        categoryService.updateCategory(id, dto);

        return "redirect:/AdminCategories";
    }

    @GetMapping("/AdminCategories/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return category.getImage().getData();
    }
    
}