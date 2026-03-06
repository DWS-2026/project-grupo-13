package com.example.demo;

import java.util.List;

public class DataService {

    public static List<Producto> getProductos() {
        return List.of(
            new Producto("Iphone 17", 1299, "Último modelo", "/imagenes/Iphone17.jpg", "Smartphones"),
            new Producto("Oppo Reno 12 5G", 499, "Gama media premium", "/imagenes/Oppo.jpg", "Smartphones"),
            new Producto("Samsung Galaxy Z Flip 7", 1099, "Plegable", "/imagenes/samsung.jpg", "Smartphones")
        );
    }
}


