package com.example.demo;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



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



private List<Review> todasLasValoraciones = new ArrayList<>();

@GetMapping("/Product")
public String verProducto(@RequestParam(name="id") int id, org.springframework.ui.Model model) {
        List<Review> valoracionesFiltradas = todasLasValoraciones.stream()
            .filter(r -> r.getProductoId() == id)
            .toList();

    // Enviamos a la vista las valoraciones específicas de este producto
    model.addAttribute("valoraciones", valoracionesFiltradas);
    
    return "Product";
}

@PostMapping("/api/valoraciones")
@ResponseBody
public String guardarValoracion(@RequestBody Review nuevaReview) {
    // Al recibir el JSON, Review ya debe traer el productoId desde el JavaScript
    todasLasValoraciones.add(nuevaReview);
    return "Valoración guardada correctamente";
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
}