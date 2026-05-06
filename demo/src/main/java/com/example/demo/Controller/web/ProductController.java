package com.example.demo.Controller.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import com.example.demo.Model.Category;







import com.example.demo.Model.Product;
import com.example.demo.Model.Review;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Security.SecurityUtils;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.CategoryService;






@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewRepository reviewService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewRepository reviewRepository;


    
    @GetMapping("/categoria/{nombre}")
    public String categoria(@PathVariable String nombre, Model model) {

        Category cat = categoryService.findByName(nombre);
            if (cat == null) {
                return "Error"; // o 404
            }

        List<Product> lista = productService.findByCategoryName(nombre);

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", nombre);

        return "Categories"; 
    }


    @PostMapping("/producto/{id}/review")
    public String guardarReview(@PathVariable int id,
                                @RequestParam String comentario,
                                Review review,
                                BindingResult result,
                                Model model) {

        Product p = productService.findById(id);
        if (p == null) {
            return "redirect:/";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username.equals("anonymousUser")) {
            return "redirect:/Login";
        }

        review.setUsuario(username);
        review.setFecha(LocalDate.now());
        review.setProduct(p);

        // SANITIZAR EL HTML ANTES DE GUARDARLO
        String comentarioSaneado = SecurityUtils.sanitize(comentario);
        review.setComentario(comentarioSaneado);

        reviewRepository.save(review);

        return "redirect:/producto/" + id;
    }


    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable int id, Model model) {

        Product p = productService.findById(id);

        if (p == null) {
            return "Error";
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