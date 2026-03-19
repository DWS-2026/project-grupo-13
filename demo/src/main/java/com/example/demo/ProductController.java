package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Product;
import com.example.demo.Review;
import com.example.demo.ProductRepository;
import com.example.demo.ReviewRepository;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewRepository reviewRepository;


    
    @GetMapping("/categoria/{nombre}")
    public String categoria(@PathVariable String nombre, Model model) {

        List<Product> lista = productService.findByCategoryName(nombre);

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", nombre);

        return "Categories"; 
    }


  
    @GetMapping("/producto/{id}")
    public String producto(@PathVariable int id, Model model) {

       Product p = productService.findById(id).orElse(null);

        if (p == null) {
            return "redirect:/"; 
        }

        model.addAttribute("producto", p);
        model.addAttribute("reviews", p.getReviews());
        model.addAttribute("nuevaReview", new Review()); // para formulario

        return "Product"; 
    }



    @PostMapping("/producto/{id}/review")
    public String guardarReview(@PathVariable int id,
                                @ModelAttribute("nuevaReview") Review review) {

        Product p = productRepository.findById(id).orElse(null);

        if (p == null) {
            return "redirect:/";
        }

        review.setProduct(p);   // clave: asociar review → producto
        reviewRepository.save(review);

        return "redirect:/producto/" + id;
    }
}