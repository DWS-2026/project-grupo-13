package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Model.User;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.SecurityUtils;
import org.springframework.web.util.HtmlUtils;



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

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    if (username.equals("anonymousUser")) {
        throw new SecurityException("Usuario no autenticado");
    }

    // Validate that the user is the owner of the review
    if (!review.getUsuario().equals(username)) {
        throw new AccessDeniedException("You do not have permission to edit this review");
    }

    // Update stars
    review.setEstrellas(newData.getEstrellas());

    // Sanitize comment (HTML allowed but safe)
    String comentarioSaneado = SecurityUtils.sanitize(newData.getComentario());
    review.setComentario(comentarioSaneado);

    // Update edit date (optional)
    review.setFecha(LocalDate.now());

    return reviewRepository.save(review);
}
    public void deleteReview(long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review no encontrada"));
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!review.getUsuario().equals(nickname)) {
        throw new AccessDeniedException("No puedes borrar una review que no es tuya");
        }

        reviewRepository.delete(review);
    }
    
    
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review saveReviewForProduct(int productId, String comentario, Review review) {

        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals("anonymousUser")) {
            throw new SecurityException("Usuario no autenticado");
        }

        
        String comentarioSaneado = SecurityUtils.sanitize(comentario);

        
        review.setUsuario(username);
        review.setFecha(LocalDate.now());
        review.setComentario(comentarioSaneado);
        review.setProduct(product);

        
        save(review);

        return review;
    }

}