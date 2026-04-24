package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ImageRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
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

    //These are methods for the ImageRestController

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