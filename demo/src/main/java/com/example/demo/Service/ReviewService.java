package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;


import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    public void save(Review review) {
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

    public void deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    
}

