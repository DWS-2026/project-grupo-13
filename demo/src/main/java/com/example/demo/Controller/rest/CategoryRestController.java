package com.example.demo.Controller.rest;

import com.example.demo.dto.CategoryBasicDTO;
import com.example.demo.dto.CategoryDetailDTO;
import com.example.demo.dto.CategoryBasicMapper;
import com.example.demo.dto.CategoryDetailMapper;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;

import com.example.demo.Model.Category;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
    public ResponseEntity<CategoryDetailDTO> createCategory (@RequestBody CategoryDetailDTO categoryDetailDTO) {

        Category category = categoryDetailMapper.toDomain(categoryDetailDTO);

        category = categoryService.createCategory(category);

        categoryDetailDTO = categoryDetailMapper.toDTO(category);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(categoryDetailDTO.id()).toUri();

        return ResponseEntity.created(location).body(categoryDetailDTO);
    }

    //replace a category
    @PutMapping("/{id}")
    public CategoryDetailDTO replaceCategory(@PathVariable long id, @RequestBody CategoryDetailDTO newCategoryDTO) {

        if (categoryService.existsById(id)) {

            Category newCategory = categoryDetailMapper.toDomain(newCategoryDTO);

            newCategory.setId(id);
            categoryService.save(newCategory);

            return categoryDetailMapper.toDTO(newCategory);

        } else {
            throw new NoSuchElementException();
        }

    }
    
    //delete a category
    @DeleteMapping("/{id}")
    public CategoryDetailDTO deletecategory(@PathVariable long id) {

        Category category = categoryService.findById(id);

        categoryService.deleteById(id);

        return categoryDetailMapper.toDTO(category);

    }
    

}
