package com.example.demo.Service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Model.OrderItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Model.User;
import com.example.demo.Model.Order;
import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private ReviewService reviewService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private ImageService imageService;

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        

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



        //Products
        Product p1 = new Product(
            "Iphone 15",
            999.99,
            "El último modelo de Apple",
            smartphones
        );
        
        setProductImage(p1, "imagenes/iphone15.jpg");
        productService.save(p1);

        
        Product p2 = new Product(
            "Iphone 12",
            1299.99,
            "El 12avo modelo de Apple",
            smartphones
        );
        
        setProductImage(p2, "imagenes/iphone12.jpg");
        productService.save(p2);

       
        Product p3 = new Product(
            "Asus Gaming",
            1099.99,
            "Portátil gaming de alta gama",
            ordenadores
        );
        
        setProductImage(p3, "imagenes/LaptopAsusGaming.jpg");
        productService.save(p3);

        
        Product p4 = new Product(
            "Asus Gaming Pro",
            1199.99,
            "Portátil gaming de alta gama a otro nivel",
            ordenadores
        );
        
        setProductImage(p4, "imagenes/LaptopAsusProArt.jpg");
        productService.save(p4);

       
        Product p5 = new Product(
            " Asus GTX 1650",
            1299.99,
            "Tarjeta gráfica de gama media para juegos y tareas creativas",
            gpus
        );
        setProductImage(p5, "imagenes/GraficaAsusGTX1650.jpg");
        productService.save(p5);

        Product p6 = new Product(
            " Asus GTX 1650",
            1299.99,
            "Tarjeta gráfica de gama media para juegos y tareas creativas",
            gpus
        );
        setProductImage(p6, "imagenes/GraficaAsusGTX1650.jpg");
        productService.save(p6);

        Product p7 = new Product(
            " Tablet Lenovo",
            199.99,
            "Tablet de última generación con lápiz",
            tablets
        );
        setProductImage(p7, "imagenes/tabletlenovo3.jpg");
        productService.save(p7);

        Product p8 = new Product(
        "Samsung Galaxy TabS10 Ultra",
        399.99,
        "Tablet muy chula para los niños",
        tablets
            );

        
        setProductImage(p8, "imagenes/TabletSamsungGalaxyTabS10Ultra.jpg");

        
        p8.setPromotion(true); 
        p8.setPrecioOriginal(499.99); 
        p8.setPrecioOferta(399.99);   

        productService.save(p8);

        Product p9 = new Product(
        "Google Pixel 9 Pro",
        799.99,
        "El nuevo Pixel con cámara avanzada y Android puro.",
        smartphones
        );
        setProductImage(p9, "imagenes/googlePixel.jpg");
        
        p9.setPromotion(true); 
        p9.setPrecioOriginal(1099.99); 
        p9.setPrecioOferta(799.99);  
        productService.save(p9);


        Product p10 = new Product(
            "MSI Katana GF66",
            1199.99,
            "Portátil gaming con RTX 4060 y pantalla de 144Hz.",
            ordenadores
        );
        setProductImage(p10, "imagenes/msiKatana.jpg");
        
        p10.setPromotion(true);
        p10.setPrecioOriginal(1199.99);
        p10.setPrecioOferta(999.99);   
        productService.save(p10);
        
        
        Product p11 = new Product(
            "NVIDIA RTX 4070 Ti SUPER",
            599.99,
            "Gráfica de última generación con DLSS 3.5 y rendimiento extremo.",
            gpus
        );
        
        setProductImage(p11, "imagenes/nvidia4070.jpg");
        p11.setPromotion(true); 
        p11.setPrecioOriginal(899.99); 
        p11.setPrecioOferta(599.99);   
        productService.save(p11);





        //Users
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

        User u3 = new User (
            "user",
            "user",
            "user@user.com",
            "user",
            passwordEncoder.encode("user"),
            "USER",
            LocalDate.of(2000, 6, 12)
        );
        userService.save(u3);

        User u4 = new User (
            "admin",
            "admin",
            "admin@admin.com",
            "admin",
            passwordEncoder.encode("admin"),
            "ADMIN",
            LocalDate.of(2000, 6, 12)
        );
        userService.save(u4);

        //Reviews for products

        Review r1 = new Review (
            "user",
            4,
            "Buen producto",
            LocalDate.of(2026, 4, 11),
            p1
        );
        reviewService.save(r1);

        Review r2 = new Review(
            "user2", 
            5, 
            "Excelente calidad",
            LocalDate.now(),
            p1
        );
        reviewService.save(r2);

        // --- ORDERS (PEDIDOS) DE PRUEBA ---

            // Pedido 1: Carlos compra un iPhone 15 y un Asus Gaming
            Order o1 = new Order();
            o1.setUser(u1); // Carlos
            o1.setFecha(LocalDateTime.now());

            OrderItem item1 = new OrderItem();
            item1.setProduct(p1); // iPhone 15
            item1.setCantidad(1);
            item1.setPrecio(p1.getPrecio());
            item1.setOrder(o1);

            OrderItem item2 = new OrderItem();
            item2.setProduct(p3); // Asus Gaming
            item2.setCantidad(1);
            item2.setPrecio(p3.getPrecio());
            item2.setOrder(o1);

            o1.setItems(List.of(item1, item2));
            // El total sería la suma de p1 + p3
            o1.setTotal(p1.getPrecio() + p3.getPrecio());

            orderService.save(o1);


            // Pedido 2: El usuario "user" compra 3 unidades de la GTX 1650
            Order o2 = new Order();
            o2.setUser(u3); // user
            o2.setFecha(LocalDateTime.now().minusDays(1)); // Ayer

            OrderItem item3 = new OrderItem();
            item3.setProduct(p5); // GTX 1650
            item3.setCantidad(3);
            item3.setPrecio(p5.getPrecio());
            item3.setOrder(o2);

            o2.setItems(List.of(item3));
            o2.setTotal(p5.getPrecio() * 3);

            orderService.save(o2);
        
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

