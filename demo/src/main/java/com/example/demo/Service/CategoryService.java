package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Service.ImageService;
import com.example.demo.dto.CategoryCreateDTO;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
    return categoryRepository.findById(id).orElse(null);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return categoryRepository.existsById(id);
    }


    public Category createCategory(CategoryCreateDTO data) {

        if (categoryRepository.existsByName(data.name())) {
            throw new IllegalArgumentException("La categoría '" + data.name() + "' ya existe");
        }

        Category category = new Category(data.name());

        try {
            if (data.image() != null && !data.image().isEmpty()) {
                Image img = imageService.createImage(data.image());
                category.setImage(img);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        return categoryRepository.save(category);
    }



    //this is a temporary fix, not used right now
    public Category createCategoryRest (Category category) {

        categoryRepository.save(category);

        return category;

    }


    ////////////////////////////////////////////// ADD AND REMOVE IMAGES ////////////////////////////////////////////////

    public Category addImageToCategory(long id, Image image) {

        Category category = categoryRepository.findById(id).orElseThrow();
        category.setImage(image);
        categoryRepository.save(category);

        return category;
    }

    public Category removeImageCategory(long id) {

        Category category = categoryRepository.findById(id).orElseThrow();

        Image image = category.getImage();
        category.setImage(null);
        categoryRepository.save(category);

        //delete the image from the BD
        if (image != null) {
            imageRepository.delete(image);
        }

        return category;
    }

}