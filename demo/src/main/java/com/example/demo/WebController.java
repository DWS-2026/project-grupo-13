package com.example.demo;



<<<<<<< HEAD



=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     @GetMapping("/AdminCategories")

    public String AdminCategories(){

        return "AdminCategories";

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

<<<<<<< HEAD





    @GetMapping("/Product")



    public String Product(){



        return "Product";
=======
        return "OrderHistory";
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e



    }



<<<<<<< HEAD

=======

private List<Review> todasLasValoraciones = new ArrayList<>();

@PostMapping("/createreview/{productId}")
public String guardarValoracion(@PathVariable long productId, Review nuevaReview) {
  
    todasLasValoraciones.add(nuevaReview);

    System.out.println(nuevaReview.getEstrellas());
    System.out.println(nuevaReview.getComentario());
    System.out.println(nuevaReview.getUsuario());

    return "redirect:/producto/" + productId;
}
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e



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


<<<<<<< HEAD
=======

    @GetMapping("/Smartphones")

    public String Smartphones(){

        return "Smartphones";
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e

    }


<<<<<<< HEAD

=======
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e
    @GetMapping("/Tablets")



    public String Tablets(){



        return "Tablets";



    }
<<<<<<< HEAD



=======
>>>>>>> 74b26fe9c3e4fe60cce6e0356be9a5ab3b3a8c9e
}