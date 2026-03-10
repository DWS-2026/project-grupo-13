package com.example.demo;

import java.util.List;

public class DataService {

    public static List<Producto> getProductos() {
        return List.of(
            new Producto(1, "Iphone 17", 1299, "Último modelo", "/imagenes/Iphone17.jpg", "Smartphones"),
            new Producto(2, "Oppo Reno 12 5G", 499, "Gama media premium", "/imagenes/Oppo.jpg", "Smartphones"),
            new Producto(3,"Samsung Galaxy Z Flip 7", 1099, "Plegable", "/imagenes/samsung.jpg", "Smartphones"),

            new Producto(4, "MacBook Pro", 1999, "Portátil profesional", "/imagenes/asus2.jpg", "Ordenadores"),
            new Producto(5, "Asus ROG Strix", 1499, "Gaming", "/imagenes/asus1.jpg", "Ordenadores"),
            new Producto(9, "Lenovo Legion", 999, "Portátil doméstico", "/imagenes/lenovo.jpg", "Ordenadores"),

            new Producto(6, "RTX 4090", 1999, "GPU tope de gama", "/imagenes/grafica1.jpg", "GPU"),
            new Producto(7, "RTX 4070", 699, "Gama media-alta", "/imagenes/grafica3.jpg", "GPU"),

            new Producto(8, "iPad Pro", 999, "Tablet profesional", "/imagenes/iphone15.jpg", "Tablets")
        );
    }
}


