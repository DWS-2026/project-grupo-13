package com.example.demo;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/AdminProduct")
    public String adminProductos(Model model) {
        model.addAttribute("producto", new Product());
        model.addAttribute("productos", productService.findAll());
        model.addAttribute("categorias", categoryService.findAll());
        return "AdminProduct"; //Devuelve a la propia página
    }

    @GetMapping("/AdminProduct/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) {
        productService.deleteById(id);
        
        return "redirect:/AdminProduct";
    }


    @PostMapping("/AdminProduct")
    public String crearProducto(@ModelAttribute Product producto,
                                @RequestParam Long category) {

        Category c = categoryService.findById(category);
        producto.setCategory(c);

        productService.save(producto);
        return "redirect:/AdminProduct";
    }

}


