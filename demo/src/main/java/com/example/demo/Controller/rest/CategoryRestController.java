package com.example.demo.Controller.rest;

import com.example.demo.dto.CategoryBasicDTO;
import com.example.demo.dto.CategoryBasicMapper;
import com.example.demo.Service.CategoryService;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    CategoryBasicMapper categoryBasicMapper;
    
    @GetMapping("/")
    public List<CategoryBasicDTO> getCategories() {
        return categoryService.findAll().stream().map(categoryBasicMapper::toDTO).toList();
    }

    /*
    @GetMapping("/{id}")
    public CategoryBasicDTO getCategory(@PathVariable Long id) {
        return categoryService.findById(id).map(this::toDTO)
    }
    */
    
}
