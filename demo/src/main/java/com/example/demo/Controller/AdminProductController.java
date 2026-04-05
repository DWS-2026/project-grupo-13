package com.example.demo.Controller;

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
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired ImageService imageService;

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
                                Model model) throws IOException { // Añadimos Model aquí para pasar el error
        
        // 1. COMPROBAR SI EXISTE: 
        // Obtenemos todos los productos y comparamos el nombre
        List<Product> todosLosProductos = productService.findAll();
        boolean nombreDuplicado = false;
        
        for (Product p : todosLosProductos) {
            if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
                nombreDuplicado = true;
                break;
            }
        }

        // 2. SI ESTÁ DUPLICADO, DEVOLVEMOS EL ERROR A LA PANTALLA
        if (nombreDuplicado) {
            model.addAttribute("errorDuplicado", true);
            model.addAttribute("nombreFallido", producto.getNombre());
            
            // Tenemos que volver a cargar las listas para que la página no se rompa
            model.addAttribute("producto", producto); // Devolvemos lo que escribió para que no se borre
            model.addAttribute("productos", productService.findAll());
            model.addAttribute("categorias", categoryService.findAll());
            
            return "AdminProduct"; // Devolvemos la vista normal, no un redirect
        }

        // 3. SI NO EXISTE, GUARDAMOS NORMAL
        Category c = categoryService.findById(category);
        producto.setCategory(c);

        if (!file.isEmpty()) {
            Image img = imageService.createImage(file);
            producto.setImage(img);
        }

        productService.save(producto);
        
        return "redirect:/AdminProduct";
    }

    @PostMapping("/AdminProducts/Edit/{id}")
    public String updateProduct(@PathVariable int id,
                                @RequestParam String nombre,
                                @RequestParam double precio,
                                @RequestParam String descripcion,
                                @RequestParam Long categoryId,
                                @RequestParam("image") MultipartFile file) {

        Product product = productRepository.findById(id).orElseThrow();

        product.setNombre(nombre);
        product.setPrecio(precio);
        product.setDescripcion(descripcion);

        Category c = categoryService.findById(categoryId);
        product.setCategory(c);

        if (!file.isEmpty()) {
            try {
                Image image = product.getImage();
                if (image == null) image = new Image();
                image.setData(file.getBytes());
                product.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        productRepository.save(product);

        return "redirect:/AdminProduct";
    }

    @GetMapping("/AdminProducts/image/{id}")
    @ResponseBody
    public byte[] getProductImage(@PathVariable int id) {
        Product product = productRepository.findById(id).orElseThrow();

        if (product.getImage() == null) {
            return new byte[0]; // evita null pointer
        }

        return product.getImage().getData();
    }

    @GetMapping("/AdminProducts/Edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("categorias", categoryService.findAll());
        return "EditProduct";
    }


}