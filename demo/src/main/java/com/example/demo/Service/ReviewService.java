package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Model.User;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(Review review) {
        if(review.getEstrellas() < 1 || review.getEstrellas() > 5){
            throw new IllegalArgumentException("Rating fuera de rango");
        }

        if (review.getComentario() != null && review.getComentario().length() > 5000) {
            throw new IllegalArgumentException("Comentario demasiado largo");
        }
        reviewRepository.save(review);
    }

    public List<Review> findByProductId(int id) {
        return reviewRepository.findByProductId(id);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(long id) {
        return reviewRepository.findById(id).orElseThrow();
    }

    public Review createReview(int productId, Review review) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        review.setProduct(product);
        review.setFecha(LocalDate.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(long reviewId, Review newData) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setUsuario(newData.getUsuario());
        review.setEstrellas(newData.getEstrellas());

        return reviewRepository.save(review);
    }

    public ResponseEntity<?> deleteReview(long reviewId) {

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticatedUser = userRepository.findByNickname(nickname);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review no encontrada"));

        if (review.getUser() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("La review no tiene usuario asignado");
        }

        if (review.getUser().getId() != authenticatedUser.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes borrar una review que no es tuya");
        }

        reviewRepository.delete(review);
        return ResponseEntity.ok("Review eliminada correctamente");
    }
    
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}