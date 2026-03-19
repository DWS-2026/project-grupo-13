package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//import com.example.demo.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_Name(String name);
}
