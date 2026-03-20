package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
/*
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
*/
import com.example.demo.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class WebController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/CategoriesScreen")
    public String categoriesScreen(Model model) {

    List<Category> categorias = categoryService.findAll();
    model.addAttribute("categorias", categorias);

    return "CategoriesScreen";
}

    @GetMapping("/Admin")
    public String Admin(){
        return "Admin";
    }

    @GetMapping("/Computers")
    public String Computers(){
        return "Computers";
    }

    @GetMapping("/EditData")
    public String EditData(){
        return "EditData";
    }

    @GetMapping("/EditProfile")
    public String EditProfile(){
        return "EditProfile";
    }

    @GetMapping("/GPU")
    public String GPU(){
        return "GPU";
    }

    @GetMapping("/Index")
    public String Index(){
        return "Index";
    }

    @GetMapping("/Login")
    public String Login(){
        return "Login";
    }

    @GetMapping("/OrderHistory")
    public String OrderHistory(){
        return "OrderHistory";
    }

    @GetMapping("/Product")
    public String Product(){
        return "Product";
    }

private List<Review> todasLasValoraciones = new ArrayList<>();

@PostMapping("/createreview/{productId}")
public String guardarValoracion(@PathVariable long productId, Review nuevaReview) {
  
    todasLasValoraciones.add(nuevaReview);

    System.out.println(nuevaReview.getComentario());
    System.out.println(nuevaReview.getUsuario());

    return "redirect:/producto/" + productId;
}

    @GetMapping("/PromotionsScreen")
    public String PromotionsScreen(){
        return "PromotionsScreen";
    }

    @GetMapping("/Register")
    public String Resgister(){
        return "Register";
    }

    @GetMapping("/ShoppingCart")
    public String ShoppingCart(){
        return "ShoppingCart";
    }

    @GetMapping("/Smartphones")
    public String Smartphones(){
        return "Smartphones";
    }

    @GetMapping("/Tablets")
    public String Tablets(){
        return "Tablets";
    }

    @GetMapping("/OrderSpecification")
    public String orderSpecification() {
    return "OrderSpecification";
    }

}