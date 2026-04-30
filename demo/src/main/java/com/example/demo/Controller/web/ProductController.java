package com.example.demo.Controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Service.ProductService;

import jakarta.validation.Valid;




@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewRepository reviewService;


    
    @GetMapping("/categoria/{nombre}")
    public String categoria(@PathVariable String nombre, Model model) {

        List<Product> lista = productService.findByCategoryName(nombre);

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", nombre);

        return "Categories"; 
    }


    @PostMapping("/producto/{id}/review")
    public String guardarReview(@PathVariable int id,
                                @Valid @ModelAttribute("nuevaReview") Review review,
                                BindingResult result,
                                Model model) {

        Product p = productService.findById(id);

        if (p == null) {
            return "redirect:/";
        }

        // Si hay errores de validación
        if (result.hasErrors()) {
            model.addAttribute("producto", p);
            model.addAttribute("valoraciones", reviewService.findByProductId(p.getId()));
            model.addAttribute("errors", result.getAllErrors());
            return "Product";
        }



        System.out.println(">>> MÉTODO guardarReview() EJECUTADO");

        review.setProduct(p);
        reviewService.save(review);

        return "redirect:/producto/" + id;
    }
    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable int id, Model model) {

        Product p = productService.findById(id);

        if (p == null) {
            return "redirect:/Login";
        }

        model.addAttribute("producto", p);
        model.addAttribute("valoraciones", reviewService.findByProductId(id));
        model.addAttribute("nuevaReview", new Review());

        return "Product"; 
    }

    @GetMapping("/PromotionsScreen")
    public String verPromociones(Model model) {
        model.addAttribute("promociones", productService.findPromotions());
        return "PromotionsScreen"; 
    }



}