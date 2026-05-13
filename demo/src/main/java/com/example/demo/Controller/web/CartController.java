package com.example.demo.Controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import com.example.demo.Model.CartItem;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderItem;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;

import org.springframework.ui.Model;

import com.example.demo.Service.ProductService;
import com.example.demo.Service.CartService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    

    private static List<CartItem> carrito = new ArrayList<>();

    @GetMapping("/ShoppingCart")
    public String verCarrito(Authentication auth, Model model) {

        String nickname = auth.getName();
        List<CartItem> carrito = cartService.getCartItems(nickname);

        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("totalArticulos", carrito.size());

        return "ShoppingCart";
    }


    @PostMapping("/agregar-carrito")
    public String agregarCarrito(@RequestParam("productId") int productId, Authentication auth) {

        if (!productService.existsById(productId)) {
            return "Error"; 
        }

        Product p = productService.findById(productId);

        CartItem item = new CartItem(
            p.getId(),
            p.getNombre(),
            "/image/" + p.getImage().getId(),
            p.getPrecio(),
            1
        );

        

        cartService.addItem(auth.getName(), item);

        return "redirect:/ShoppingCart";
    }



    @PostMapping("/eliminar-producto")
        public String eliminarProducto(@RequestParam("productId") int productId, Authentication auth) {
            cartService.removeItem(auth.getName(), productId);
            return "redirect:/ShoppingCart";
        }



    @GetMapping("/api/carrito")
    @ResponseBody
    public List<CartItem> obtenerCarrito(Authentication auth) {
        return cartService.getCartItems(auth.getName());
    }


    


}