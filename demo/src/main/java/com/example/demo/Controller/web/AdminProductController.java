package com.example.demo.Controller.web;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;

import com.example.demo.Model.Category;
import com.example.demo.Model.Image;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ImageService;

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
        return "AdminProduct"; 
    }

    @GetMapping("/AdminProduct/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) {
        productService.deleteById(id);
        return "redirect:/AdminProduct";
    }

    @PostMapping("/AdminProduct")
    public String crearProducto(@ModelAttribute Product producto,
                                @RequestParam Long category, 
                                @RequestParam("productImage") MultipartFile file,
                                Model model) throws IOException {
        
        Product result = productService.createProduct(producto, category, file);

        if (result == null) {
            model.addAttribute("errorDuplicado", true);
            model.addAttribute("nombreFallido", producto.getNombre());
            model.addAttribute("producto", producto);
            model.addAttribute("productos", productService.findAll());
            model.addAttribute("categorias", categoryService.findAll());
            return "AdminProduct";
        }

        return "redirect:/AdminProduct";
    }

    @PostMapping("/AdminProducts/Edit/{id}")
    public String updateProduct(@PathVariable int id,
                                @RequestParam String nombre,
                                @RequestParam double precio,
                                @RequestParam String descripcion,
                                @RequestParam Long categoryId,
                                @RequestParam("image") MultipartFile file) throws IOException {

        productService.updateProduct(id, nombre, precio, descripcion, categoryId, file);
        return "redirect:/AdminProduct";
    }

    @GetMapping("/AdminProducts/Edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categorias", categoryService.findAll());
        return "EditProduct";
    }

    @GetMapping("/AdminProducts/image/{id}")
    @ResponseBody
    public byte[] getProductImage(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product == null || product.getImage() == null) {
            return new byte[0];
        }
        return product.getImage().getData();
    }

}