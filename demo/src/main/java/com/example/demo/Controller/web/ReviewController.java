package com.example.demo.Controller.web;

import com.example.demo.Model.User;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Model.Review;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ReviewService;
import com.example.demo.Service.UserService;
import com.example.demo.Model.Product;
import org.springframework.ui.Model;
import com.example.demo.Security.SecurityUtils;

@Controller
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @PostMapping("/reviews/add")
    public String addReview(
        @RequestParam int estrellas,
        @RequestParam String comentario,
        @RequestParam Long productId,
        Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/Login";
        }

        Product product = productService.findById(productId.intValue());
        if (product == null){
            return "Error";
        }

        if (estrellas < 1 || estrellas > 5){
            model.addAttribute("errorEstrellas", true);
            return "redirect:/producto/" + productId;
        }

        // Buscamos el usuario en la BD para vincularlo a la reseña
        User currentUser = userService.findByNickname(principal.getName());
        
        // Saneamos el comentario
        String comentarioSaneado = SecurityUtils.sanitize(comentario);

        Review review = new Review();
        review.setEstrellas(estrellas);
        review.setComentario(comentarioSaneado); // <- Usamos el comentario limpio
        review.setUsuario(currentUser.getNickname());
        review.setUser(currentUser); // <- CRÍTICO: vinculamos el objeto User
        review.setProduct(product);

        reviewService.save(review);

        return "redirect:/producto/" + productId;
    }

    @PostMapping("/review/eliminar/{id}")
public String eliminarValoracion(@PathVariable Long id, @RequestParam Long productoId, java.security.Principal principal) {
    
    if (principal == null) return "redirect:/Login";

    Review review = reviewService.findById(id);
    
    // Solo borramos si el nombre del autor coincide con el usuario conectado
    if (review != null && principal.getName().equals(review.getUsuario())) {
        reviewService.deleteById(id);
    }
    
    return "redirect:/producto/" + productoId; 
}
    
}