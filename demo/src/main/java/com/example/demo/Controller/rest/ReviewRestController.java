package com.example.demo.Controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.example.demo.Service.ProductService;
import com.example.demo.Service.ImageService;
import com.example.demo.dto.ProductBasicDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.ReviewDetailDTO;
import com.example.demo.dto.mapper.ProductBasicMapper;
import com.example.demo.dto.mapper.ProductDetailMapper;
import com.example.demo.dto.mapper.ReviewDetailMapper;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.Service.ReviewService;
import com.example.demo.Model.User;
import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

//There is only one DTO for the entity "Review"

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {
    
    @Autowired
    private ReviewDetailMapper reviewDetailMapper;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    //show all reviews in the DB
    @GetMapping("/")
    public Page<ReviewDetailDTO> getReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewDetailMapper::toDTO);
    }

    //show one detailed review
    @GetMapping("/{id}")
    public ReviewDetailDTO getReview(@PathVariable long id) {
        return reviewDetailMapper.toDTO(reviewService.findById(id));
    }

    @PostMapping("/products/{productId}/reviews")
    @Transactional
    public Review createReview( @PathVariable int productId,
        @RequestBody Review review) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticatedUser = userRepository.findByNickname(nickname);

        if (authenticatedUser == null) {
            review.setUsuario("anonimo");
        } else {
            review.setUsuario(authenticatedUser.getNickname());
            review.setUser(authenticatedUser);
        }

        review.setFecha(LocalDate.now());
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable long reviewId,
            @RequestBody Review newData) {

        Review updated = reviewService.updateReview(reviewId, newData);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int id) {
        return reviewService.deleteReview(id);
    }

}
