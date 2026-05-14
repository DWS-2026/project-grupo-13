package com.example.demo.Controller.rest;

import com.example.demo.Repository.ProductRepository;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.NoSuchElementException;

import com.example.demo.Service.ProductService;
import com.example.demo.Service.ImageService;
import com.example.demo.dto.ProductBasicDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.ProductCreateDTO;
import com.example.demo.dto.mapper.ProductBasicMapper;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.mapper.ProductDetailMapper;
import com.example.demo.Service.ReviewService;
import jakarta.persistence.EntityNotFoundException;

import com.example.demo.Model.Product;
import com.example.demo.Model.Review;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductBasicMapper productBasicMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    // Show all products in the DB
    @GetMapping("/")
    public Page<ProductBasicDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productBasicMapper::toDTO);
    }
    
    // Show one detailed product
    @GetMapping("/{id}")
    public ProductDetailDTO getproduct(@PathVariable int id) {
        return productDetailMapper.toDTO(productService.findById(id));
    }

    // Create a new product
    @PostMapping("/")
    public ResponseEntity<ProductDetailDTO> createProduct(@RequestBody ProductCreateDTO dto) {
        ProductDetailDTO created = productService.createProduct(dto);

        URI location = fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ProductDetailDTO deleteProduct(@PathVariable int id) {
        // Now the service is responsible for finding and deleting it (including image logic)
        return productService.deleteProductById(id);
    }

    // Replace a product
    @PutMapping("/{id}")
    public ProductDetailDTO replaceProduct(@PathVariable int id, @RequestBody ProductCreateDTO dto) {
        // We use ProductCreateDTO for PUT as well, allowing image updates
        return productService.updateProduct(id, dto);
    }

    @PostMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> assignCategoryToProduct(
            @PathVariable int productId,
            @PathVariable long categoryId) {

        productService.assignCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/reviews")
    @Transactional
    public ResponseEntity<Review> createReview(
            @PathVariable int productId,
            @RequestBody Review review) {

        try {
            Review saved = reviewService.saveReviewForProduct(
                    productId,
                    review.getComentario(),
                    review
            );
            return ResponseEntity.ok(saved);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


