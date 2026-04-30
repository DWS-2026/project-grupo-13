package com.example.demo.Controller.rest;

import com.example.demo.dto.CategoryBasicDTO;
import com.example.demo.dto.CategoryDetailDTO;
import com.example.demo.dto.CategoryBasicMapper;
import com.example.demo.dto.CategoryDetailMapper;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;

import com.example.demo.dto.UserBasicMapperImpl;
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
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/categories")
public class CategoryRestController {

    private final UserBasicMapperImpl userBasicMapperImpl;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBasicMapper categoryBasicMapper;

    @Autowired
    private CategoryDetailMapper categoryDetailMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    CategoryRestController(UserBasicMapperImpl userBasicMapperImpl) {
        this.userBasicMapperImpl = userBasicMapperImpl;
    }
    
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

    
    
    
}
