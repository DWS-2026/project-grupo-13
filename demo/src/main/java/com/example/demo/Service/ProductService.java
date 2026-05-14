package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.dto.ProductCreateDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.mapper.ProductDetailMapper;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Model.Image;
import com.example.demo.Model.Category;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ImageService imageService;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    /*
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();

        reviewRepository.deleteAll(product.getReviews());
        productRepository.delete(product);
    }
    */

    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }

    public List<Product> findByCategoryName(String category) {
        return productRepository.findByCategory_Name(category);
    }

    public List<Product> findPromotions() {
        return productRepository.findByPromotionTrue();
    }

    /**
     * Creates a product from the REST API using a DTO.
     * If the image in the DTO is null or empty, the product is created without an image.
     */
    public ProductDetailDTO createProduct(ProductCreateDTO dto) {
        Product product = new Product();
        return mapAndSave(product, dto);
    }

    /**
     * Updates a product from the REST API.
     * Based on unified image logic.
     */
    public ProductDetailDTO updateProduct(int id, ProductCreateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        
        return mapAndSave(product, dto);
    }

    /**
     * Private method to unify data mapping and image logic 
     * between creation and update in the API.
     */
    private ProductDetailDTO mapAndSave(Product product, ProductCreateDTO dto) {

        product.setNombre(dto.nombre());
        product.setPrecio(dto.precio());
        product.setDescripcion(dto.descripcion());
        product.setPromotion(dto.promotion());
        product.setPrecioOriginal(dto.precioOriginal());
        product.setPrecioOferta(dto.precioOferta());

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist"));
        product.setCategory(category);

        try {
            if (dto.image() != null && !dto.image().isEmpty()) {
                Image img = imageService.createImage(dto.image());
                product.setImage(img);
            }
            // IMPORTANTE: si dto.image() es null o vacío, NO tocamos la imagen existente
        } catch (IOException e) {
            throw new RuntimeException("Error processing product image", e);
        }

        Product saved = productRepository.save(product);
        return productDetailMapper.toDTO(saved);
    }


    /**
     * Deletes a product and returns its DTO.
     * Fixes the image_841f04.png error using ResponseStatusException.
     */
    public ProductDetailDTO deleteProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        ProductDetailDTO dto = productDetailMapper.toDTO(product);
        
        Image img = product.getImage();
        productRepository.delete(product);

        // Clean up the image in the DB if it existed
        if (img != null) {
            imageRepository.delete(img);
        }

        return dto;
    }

    // These are methods for the ImageRestController

    public Product addImageToProduct(int id, Image image) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setImage(image);
        productRepository.save(product);
        return product;
    }

    public Product removeImageProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        Image image = product.getImage();
        product.setImage(null);
        productRepository.save(product);

        // Delete the image from the DB
        if (image != null) {
            imageRepository.delete(image);
        }
        return product;
    }

    public void assignCategory(int productId, long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);
        productRepository.save(product);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    /*

    public Product createProduct(Product product, Long categoryId, MultipartFile file) throws IOException {
        // Validation: Duplicate name check
        boolean isDuplicate = productRepository.findAll().stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(product.getNombre()));

        if (isDuplicate) {
            return null;
        }

        // Set Category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        // Handle Image
        if (file != null && !file.isEmpty()) {
            Image img = imageService.createImage(file);
            product.setImage(img);
        } else {
            product.setImage(null);
        }

        return productRepository.save(product);
    }

    
    public void updateProduct(int id, String nombre, double precio, String descripcion, Long categoryId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setNombre(nombre);
        product.setPrecio(precio);
        product.setDescripcion(descripcion);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        if (file != null && !file.isEmpty()) {
            Image image = product.getImage();
            if (image == null) {
                image = new Image();
            }
            image.setData(file.getBytes());
            product.setImage(image);
        }

        productRepository.save(product);
    }

    */
}