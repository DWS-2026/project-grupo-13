package com.example.demo.Controller.rest;

import com.example.demo.dto.CategoryBasicDTO;
import com.example.demo.dto.CategoryCreateDTO;
import com.example.demo.dto.CategoryDetailDTO;
import com.example.demo.dto.mapper.CategoryBasicMapper;
import com.example.demo.dto.mapper.CategoryDetailMapper;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;

import com.example.demo.Model.Category;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("api/v1/categories")
public class CategoryRestController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBasicMapper categoryBasicMapper;

    @Autowired
    private CategoryDetailMapper categoryDetailMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    
    //show all categories
    @GetMapping("/")
    public Page<CategoryBasicDTO> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryBasicMapper::toDTO);
    }

    //show one detailed category
    @GetMapping("/{id}")
    public CategoryDetailDTO getCategory(@PathVariable Long id) {
        return categoryDetailMapper.toDTO(categoryService.findById(id));
    }

    //create a category
    @PostMapping("/")
    public ResponseEntity<CategoryDetailDTO> createCategory(@RequestBody Map<String, String> body) {

        String name = body.get("name");

        CategoryCreateDTO dto = new CategoryCreateDTO(name, null);
        Category category = categoryService.createCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(categoryDetailMapper.toDTO(category));
    }

    //replace a category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String name = body.get("name");

        
        
        try{

            CategoryCreateDTO dto = new CategoryCreateDTO(name, null);
            Category updated = categoryService.updateCategory(id, dto);
            return ResponseEntity.ok(categoryDetailMapper.toDTO(updated));
        
        }catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada");
        }

        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {

        try {
            Category category = categoryService.findById(id); 
            categoryService.deleteById(id);

            return ResponseEntity.ok("Categoría eliminada correctamente");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada");
        }
    }


}
