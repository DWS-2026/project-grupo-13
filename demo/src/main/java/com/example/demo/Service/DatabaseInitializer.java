package com.example.demo.Service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;

import java.io.IOException;
import java.net.URISyntaxException;

import java.time.LocalDate;

/*import com.example.demo.Category;
import com.example.demo.Image;
import com.example.demo.ImageService;

import com.example.demo.Product;
import com.example.demo.User;

import java.util.ArrayList;
import java.util.Arrays;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.demo.ProductService;
import com.example.demo.UserService;
*/


@Service
public class DatabaseInitializer {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private ImageService imageService;

    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        // 4 categorías base
        // Crear categorías base

        // Smartphones
        Category smartphones = new Category("Smartphones");
        categoryService.save(smartphones);
        setCategoryImage(smartphones, "imagenes/smart.jpg");
        categoryService.save(smartphones);

        // Ordenadores
        Category ordenadores = new Category("Ordenadores");
        categoryService.save(ordenadores);
        setCategoryImage(ordenadores, "imagenes/pcs.jpg");
        categoryService.save(ordenadores);

        // GPUs
        Category gpus = new Category("GPUs");
        categoryService.save(gpus);
        setCategoryImage(gpus, "imagenes/tjs.jpg");
        categoryService.save(gpus);

        // Tablets
        Category tablets = new Category("Tablets");
        categoryService.save(tablets);
        setCategoryImage(tablets, "imagenes/TabletLenovoTabM11.jpg");
        categoryService.save(tablets);



        //Productos
        Product p1 = new Product(
            "Iphone 15",
            999.99,
            "El último modelo de Apple",
            smartphones
        );
        // imagen del producto
        setProductImage(p1, "imagenes/iphone15.jpg");
        productService.save(p1);

        //Usuarios
        User u1 = new User(
        "Carlos",
        "García",
        "carlos.garcia@example.com",
        "cgarcia",
        passwordEncoder.encode("carlos123"),
        "USER",
        LocalDate.of(1998, 5, 12)
        );
        userService.save(u1);


        User u2 = new User(
        "Laura",
        "Martínez",
        "laura.martinez@example.com",
        "lauram",
        passwordEncoder.encode("admin456"),
        "ADMIN",
        LocalDate.of(1992, 11, 3)
        );
        userService.save(u2); 
    }

    public void setProductImage(Product product, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        Image createdImage = imageService.createImage(image.getInputStream());
        product.setImage(createdImage);
    }

    public void setCategoryImage(Category category, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        Image createdImage = imageService.createImage(image.getInputStream());
        category.setImage(createdImage);
    }

}

