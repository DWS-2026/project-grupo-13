package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WebController {


    @GetMapping("/CategoriesScreen")
    public String CategoriesScreen(){
        return "CategoriesScreen";
    }

    @GetMapping("/Admin")
    public String Admin(){
        return "Admin";
    }

    @GetMapping("/AdminProduct")
    public String AdminProduct(){
        return "AdminProduct";
    }

    @GetMapping("/AdminUser")
    public String AdminUser(){
        return "AdminUser";
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

    @GetMapping("/Product")
    public String Product(){
        return "Product";
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

    @GetMapping("/Tablets")
    public String Tablets(){
        return "Tablets";
    }
}