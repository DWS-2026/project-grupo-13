package com.example.demo.Controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ImageMapper;
import com.example.demo.Model.Image;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.ImageService;
import com.example.demo.Service.ProductService;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("api/images")
public class ImageRestController {
    
    @Autowired 
    private CategoryService categoryService;
    
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ImageDTO getImage(@PathVariable long id) {
        return imageMapper.toDTO(imageService.getImage(id));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<byte[]> getImageMedia(@PathVariable long id) {

        Image image = imageService.getImage(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getData());
    }

    //Methods for product images
    @PostMapping("/{id}/images")
    public ResponseEntity<ImageDTO> createProductImage(@PathVariable int id, 
        @RequestParam MultipartFile imageFile) throws IOException {

            if (imageFile.isEmpty()) {
                throw new IllegalArgumentException("The file cannot be empty");
            }

            Image image = imageService.createImage(imageFile.getInputStream());
            productService.addImageToProduct(id, image);

            URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/{imageId}/media")
                .buildAndExpand(image.getId())
                .toUri();

            return ResponseEntity.created(location).body(imageMapper.toDTO(image));
    }


    @PutMapping("/{id}/images")
    public ResponseEntity<Void> replaceImage(
            @PathVariable long id,
            @RequestParam MultipartFile imageFile) throws IOException {

        imageService.replaceImage(id, imageFile.getBytes());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ImageDTO deleteProductImage(@PathVariable int productId, @PathVariable long imageId) {

        Image image = imageService.getImage(imageId);
        productService.removeImageProduct(productId);
        imageService.deleteImage(imageId);

        return imageMapper.toDTO(image);
    }

    //Methods for user images
    @PostMapping("/users/{id}/image")
    public ResponseEntity<ImageDTO> createUserImage(
            @PathVariable long id,
            @RequestParam MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("The file cannot be empty");
        }

        Image image = imageService.createImage(imageFile.getInputStream());
        userService.addImageToUser(id, image);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/{imageId}/media")
                .buildAndExpand(image.getId())
                .toUri();

        return ResponseEntity.created(location).body(imageMapper.toDTO(image));
    }
    @PutMapping("/users/{id}/image")
    public ResponseEntity<Void> replaceUserImage(
            @PathVariable long id,
            @RequestParam MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("The file cannot be empty");
        }

        imageService.replaceImage(id, imageFile.getBytes());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/image/{imageId}")
    public ImageDTO deleteUserImage(
            @PathVariable long userId,
            @PathVariable long imageId) {

        Image image = imageService.getImage(imageId);

        userService.removeImageFromUser(userId);
        imageService.deleteImage(imageId);

        return imageMapper.toDTO(image);
    }



    //Methods for category images
    @PostMapping("/categories/{id}/images")
    public ResponseEntity<ImageDTO> createCategoryImage(@PathVariable int id, 
        @RequestParam MultipartFile imageFile) throws IOException {

            if (imageFile.isEmpty()) {
                throw new IllegalArgumentException("The file cannot be empty");
            }

            Image image = imageService.createImage(imageFile.getInputStream());
            // Requiere que categoryService y el método addImageToCategory existan
            categoryService.addImageToCategory(id, image); 

            URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/images/{imageId}/media")
                .buildAndExpand(image.getId())
                .toUri();

            return ResponseEntity.created(location).body(imageMapper.toDTO(image));
    }

    @DeleteMapping("/categories/{categoryId}/images/{imageId}")
    public ImageDTO deleteCategoryImage(@PathVariable int categoryId, @PathVariable long imageId) {

        Image image = imageService.getImage(imageId);
        // Requiere que categoryService y el método removeImageCategory existan
        categoryService.removeImageCategory(categoryId); 
        imageService.deleteImage(imageId);

        return imageMapper.toDTO(image);
    }

}
