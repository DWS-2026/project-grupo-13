package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.Category;
import com.example.demo.Model.Review;
import com.example.demo.Service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private CategoryService categoryService;

    // --- CATEGORÍAS ---
    @GetMapping("/CategoriesScreen")
    public String categoriesScreen(Model model) {
        List<Category> categorias = categoryService.findAll();
        model.addAttribute("categorias", categorias);
        return "CategoriesScreen";
    }

    // --- LOGIN CON DETECCIÓN DE ERROR ---
    @GetMapping("/Login")
    public String Login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorLogin", true);
        }
        return "Login";
    }

    // --- PÁGINAS PRINCIPALES Y NAVEGACIÓN ---
    @GetMapping("/Index")
    public String Index() {
        return "Index";
    }

    

    @GetMapping("/ShoppingCart")
    public String ShoppingCart() {
        return "ShoppingCart";
    }

    @GetMapping("/OrderHistory")
    public String OrderHistory() {
        return "OrderHistory";
    }

    // --- VISTAS DE PRODUCTOS ---
    @GetMapping("/Product")
    public String Product() {
        return "Product";
    }

    @GetMapping("/Computers")
    public String Computers() {
        return "Computers";
    }

    @GetMapping("/GPU")
    public String GPU() {
        return "GPU";
    }

    @GetMapping("/Smartphones")
    public String Smartphones() {
        return "Smartphones";
    }

    @GetMapping("/Tablets")
    public String Tablets() {
        return "Tablets";
    }

    // --- REGISTRO ---
    @GetMapping("/register")
    public String resgister() {
        return "Register";
    }

    // --- ADMINISTRACIÓN ---
    @GetMapping("/Admin")
    public String Admin() {
        return "Admin";
    }

   


    // --- VALORACIONES ---
    private List<Review> todasLasValoraciones = new ArrayList<>();

    @PostMapping("/createreview/{productId}")
    public String guardarValoracion(@PathVariable long productId, Review nuevaReview) {
        todasLasValoraciones.add(nuevaReview);
        System.out.println("Comentario: " + nuevaReview.getComentario());
        System.out.println("Usuario: " + nuevaReview.getUsuario());
        return "redirect:/producto/" + productId;
    }
}