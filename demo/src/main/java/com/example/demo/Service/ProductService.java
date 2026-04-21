package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    
    public Product save(Product product) {
        return productRepository.save(product);
    }

   
    public List<Product> findAll() {
        return productRepository.findAll();
    }

   
    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow();
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

        /*
        if(product.getId() != null) {
            throw new IllegalArgumentException();
        }
        */

        productRepository.save(product);

        return product;

    }


}

