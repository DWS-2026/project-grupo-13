package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Product;

import java.util.List;
//import com.example.demo.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_Name(String name);

    List<Product> findByPromotionTrue();

}
