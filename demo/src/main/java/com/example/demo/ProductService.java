package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Product;
import com.example.demo.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Guardar producto
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Obtener todos los productos
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Buscar por ID
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Eliminar por ID
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    // Buscar por categoría
    public List<Product> findByCategoryName(String category) {
        return productRepository.findByCategory_Name(category);
    }
}

