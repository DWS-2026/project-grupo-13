package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private static List<CartItem> carrito = new ArrayList<>();

    @PostMapping("/agregar-carrito")
    public String agregarAlCarrito(
            @RequestParam("nombreProducto") String nombre,
            @RequestParam("imagenProducto") String imagen,
            @RequestParam("precioProducto") double precio) { // Recibimos el precio
        
        carrito.add(new CartItem(nombre, imagen, precio));
        
        System.out.println("-------------------------------------------");
        System.out.println("¡AÑADIDO: " + nombre + " por " + precio + "€!");
        System.out.println("-------------------------------------------");
        
        return "redirect:/Index"; 
    }

    @PostMapping("/eliminar-producto")
    @ResponseBody
    public String eliminarProducto(@RequestParam("nombre") String nombre) {
        carrito.removeIf(item -> item.getNombre().equals(nombre));
        return "OK";
    }

    @GetMapping("/api/carrito")
    @ResponseBody
    public List<CartItem> obtenerCarrito() {
        return carrito;
    }
}