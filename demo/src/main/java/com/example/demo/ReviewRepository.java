package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Opcional: por si quieres obtener reviews de un producto concreto
    List<Review> findByProductId(Integer productId);
}