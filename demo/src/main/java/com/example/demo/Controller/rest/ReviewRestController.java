package com.example.demo.Controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.demo.dto.ProductBasicMapper;
import com.example.demo.dto.ProductDetailMapper;

import com.example.demo.dto.ReviewDetailDTO;
import com.example.demo.dto.ReviewDetailMapper;
import com.example.demo.Service.ReviewService;

import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Repository.ReviewRepository;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

//There is only one DTO for the entity "Review"

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {
    
    @Autowired
    private ReviewDetailMapper reviewDetailMapper;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

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
    public ResponseEntity<Review> createReview(
            @PathVariable int productId,   // usa int, no long
            @RequestBody Review review) {

        Review saved = reviewService.createReview(productId, review);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }



    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable long reviewId,
            @RequestBody Review newData) {

        Review updated = reviewService.updateReview(reviewId, newData);
        return ResponseEntity.ok(updated);
    }



    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }




}
