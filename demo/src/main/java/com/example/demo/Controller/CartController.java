package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Model.CartItem;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private static List<CartItem> carrito = new ArrayList<>();

    @PostMapping("/agregar-carrito")
    public String agregarAlCarrito(
            @RequestParam("nombreProducto") String nombre,
            @RequestParam("imagenProducto") String imagen,
            @RequestParam("precioProducto") double precio) { 
        
        carrito.add(new CartItem(nombre, imagen, precio));
        
        System.out.println("-------------------------------------------");
        System.out.println("¡AÑADIDO: " + nombre + " por " + precio + "€!");
        System.out.println("-------------------------------------------");
        
        return "redirect:/Index"; 
    }

    @PostMapping("/eliminar-producto")
    @ResponseBody
    public String eliminarProducto(@RequestParam("nombre") String nombre) {
        // Eliminamos usando trim() para asegurar que coincida exactamente
        carrito.removeIf(item -> item.getNombre().trim().equalsIgnoreCase(nombre.trim()));
        return "OK";
    }

    @GetMapping("/api/carrito")
    @ResponseBody
    public List<CartItem> obtenerCarrito() {
        return carrito;
    }
}