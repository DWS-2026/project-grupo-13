package com.example.demo.Controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Review;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ReviewService;


@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;


    @PostMapping("/reviews/add")
    public String addReview(
        @RequestParam int estrellas,
        @RequestParam String comentario,
        @RequestParam Long productId,
        Principal principal) {

    if (principal == null) {
        return "redirect:/Login";
    }

    Review review = new Review();
    review.setEstrellas(estrellas);
    review.setComentario(comentario);
    review.setUsuario(principal.getName());
    review.setProduct(productService.findById(productId.intValue()));



    reviewService.save(review);

    return "redirect:/producto/" + productId;

}

}
