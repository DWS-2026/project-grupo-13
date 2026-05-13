package com.example.demo.Controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
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
import org.springframework.security.access.AccessDeniedException;
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

    

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable long reviewId,
            @RequestBody Review newData) {

        try {
            Review updated = reviewService.updateReview(reviewId, newData);
            return ResponseEntity.ok(updated);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para editar esta review");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review no encontrada");
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable long id) {

        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review eliminada correctamente");

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para borrar esta review");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review no encontrada");
        }
    }

}
