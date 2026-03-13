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
            @RequestParam("imagenProducto") String imagen) {
        
        carrito.add(new CartItem(nombre, imagen));
        
        System.out.println("-------------------------------------------");
        System.out.println("¡AÑADIDO CON IMAGEN!");
        System.out.println("Producto: " + nombre);
        System.out.println("-------------------------------------------");
        
        return "redirect:/Index"; 
    }

    // --- ESTE ES EL MÉTODO QUE TE FALTABA ---
    @PostMapping("/eliminar-producto")
    @ResponseBody
    public String eliminarProducto(@RequestParam("nombre") String nombre) {
        // Buscamos en la lista y eliminamos el que coincida con el nombre
        carrito.removeIf(item -> item.getNombre().equals(nombre));
        
        System.out.println("-------------------------------------------");
        System.out.println("¡ELIMINADO!");
        System.out.println("Producto: " + nombre);
        System.out.println("-------------------------------------------");
        
        return "OK"; // Respondemos OK al JavaScript para que refresque la pantalla
    }

    @GetMapping("/api/carrito")
    @ResponseBody
    public List<CartItem> obtenerCarrito() {
        return carrito;
    }
}