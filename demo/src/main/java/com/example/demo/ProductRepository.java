package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.Producto;

public interface ProductRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
