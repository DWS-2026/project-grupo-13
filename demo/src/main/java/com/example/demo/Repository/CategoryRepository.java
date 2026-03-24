package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Category findByName(String name);
    
    // Añade esta línea para que el controlador funcione a la perfección:
    boolean existsByName(String name);
}