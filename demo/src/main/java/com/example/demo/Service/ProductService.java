package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.CategoryRepository;

import com.example.demo.Model.Image;
import com.example.demo.Model.Category;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    
    public Product save(Product product) {
        return productRepository.save(product);
    }

   
    public List<Product> findAll() {
        return productRepository.findAll();
    }

   
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }

    
    public List<Product> findByCategoryName(String category) {
        return productRepository.findByCategory_Name(category);
    }

    public List<Product> findPromotions() {
    return productRepository.findByPromotionTrue();
    }

    public Product createProduct(Product product) {

        productRepository.save(product);

        return product;

    }

    //These are methods for the ImageRestController

    public Product addImageToProduct(int id, Image image) {

        Product product = productRepository.findById(id).orElseThrow();
        product.setImage(image);
        productRepository.save(product);

        return product;
    }

    public Product removeImageProduct(int id) {

        Product product = productRepository.findById(id).orElseThrow();

        Image image = product.getImage();
        product.setImage(null);
        productRepository.save(product);

        //delete the image from the BD
        if (image != null) {
            imageRepository.delete(image);
        }

        return product;
    }

    public void assignCategory(int productId, long categoryId) {

        Product product = productRepository.findById(productId)
                .orElseThrow();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow();

        product.setCategory(category);
        productRepository.save(product);
    }


}

